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
        name = "gAEUserApi",
        version = "v1",
        resource = "gAEUser",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.TheoP.example.com",
                ownerName = "backend.myapplication.TheoP.example.com",
                packagePath = ""
        )
)
public class GAEUserEndpoint {

    private static final Logger logger = Logger.getLogger(GAEUserEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(GAEUser.class);
    }

    /**
     * Returns the {@link GAEUser} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code GAEUser} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "gAEUser/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public GAEUser get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting GAEUser with ID: " + id);
        GAEUser gAEUser = ofy().load().type(GAEUser.class).id(id).now();
        if (gAEUser == null) {
            throw new NotFoundException("Could not find GAEUser with ID: " + id);
        }
        return gAEUser;
    }

    /**
     * Inserts a new {@code GAEUser}.
     */
    @ApiMethod(
            name = "insert",
            path = "gAEUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public GAEUser insert(GAEUser gAEUser) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that gAEUser.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. Long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(gAEUser).now();
        logger.info("Created GAEUser with ID: " + gAEUser.getId());

        return ofy().load().entity(gAEUser).now();
    }

    /**
     * Updates an existing {@code GAEUser}.
     *
     * @param id      the ID of the entity to be updated
     * @param gAEUser the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAEUser}
     */
    @ApiMethod(
            name = "update",
            path = "gAEUser/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public GAEUser update(@Named("id") Long id, GAEUser gAEUser) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(gAEUser).now();
        logger.info("Updated GAEUser: " + gAEUser);
        return ofy().load().entity(gAEUser).now();
    }

    /**
     * Deletes the specified {@code GAEUser}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAEUser}
     */
    @ApiMethod(
            name = "remove",
            path = "gAEUser/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(GAEUser.class).id(id).now();
        logger.info("Deleted GAEUser with ID: " + id);
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
            path = "gAEUser",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<GAEUser> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<GAEUser> query = ofy().load().type(GAEUser.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<GAEUser> queryIterator = query.iterator();
        List<GAEUser> gAEUserList = new ArrayList<GAEUser>(limit);
        while (queryIterator.hasNext()) {
            gAEUserList.add(queryIterator.next());
        }
        return CollectionResponse.<GAEUser>builder().setItems(gAEUserList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(GAEUser.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find GAEUser with ID: " + id);
        }
    }
}