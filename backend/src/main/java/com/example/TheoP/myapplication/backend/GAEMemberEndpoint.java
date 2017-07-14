package com.example.TheoP.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "gAEMemberApi",
        version = "v1",
        resource = "gAEMember",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.TheoP.example.com",
                ownerName = "backend.myapplication.TheoP.example.com",
                packagePath = ""
        )
)
public class GAEMemberEndpoint {

    private static final Logger logger = Logger.getLogger(GAEMemberEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(GAEMember.class);
    }

    /**
     * Returns the {@link GAEMember} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code GAEMember} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "gAEMember/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public GAEMember get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting GAEMember with ID: " + id);
        GAEMember gAEMember = ofy().load().type(GAEMember.class).id(id).now();
        if (gAEMember == null) {
            throw new NotFoundException("Could not find GAEMember with ID: " + id);
        }
        return gAEMember;
    }

    /**
     * Inserts a new {@code GAEMember}.
     */
    @ApiMethod(
            name = "insert",
            path = "gAEMember",
            httpMethod = ApiMethod.HttpMethod.POST)
    public GAEMember insert(GAEMember gAEMember) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that gAEMember.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(gAEMember).now();
        logger.info("Created GAEMember.");

        return ofy().load().entity(gAEMember).now();
    }

    /**
     * Updates an existing {@code GAEMember}.
     *
     * @param id        the ID of the entity to be updated
     * @param gAEMember the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAEMember}
     */
    @ApiMethod(
            name = "update",
            path = "gAEMember/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public GAEMember update(@Named("id") long id, GAEMember gAEMember) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(gAEMember).now();
        logger.info("Updated GAEMember: " + gAEMember);
        return ofy().load().entity(gAEMember).now();
    }

    /**
     * Deletes the specified {@code GAEMember}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAEMember}
     */
    @ApiMethod(
            name = "remove",
            path = "gAEMember/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(GAEMember.class).id(id).now();
        logger.info("Deleted GAEMember with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "gAEMember",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<GAEMember> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<GAEMember> query = ofy().load().type(GAEMember.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<GAEMember> queryIterator = query.iterator();
        List<GAEMember> gAEMemberList = new ArrayList<GAEMember>(limit);
        while (queryIterator.hasNext()) {
            gAEMemberList.add(queryIterator.next());
        }
        return CollectionResponse.<GAEMember>builder().setItems(gAEMemberList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(GAEMember.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find GAEMember with ID: " + id);
        }
    }
}