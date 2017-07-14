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
        name = "gAECommunityTypeApi",
        version = "v1",
        resource = "gAECommunity_Type",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.TheoP.example.com",
                ownerName = "backend.myapplication.TheoP.example.com",
                packagePath = ""
        )
)
public class GAECommunity_TypeEndpoint {

    private static final Logger logger = Logger.getLogger(GAECommunity_TypeEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(GAECommunity_Type.class);
    }

    /**
     * Returns the {@link GAECommunity_Type} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code GAECommunity_Type} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "gAECommunity_Type/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public GAECommunity_Type get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting GAECommunity_Type with ID: " + id);
        GAECommunity_Type gAECommunity_Type = ofy().load().type(GAECommunity_Type.class).id(id).now();
        if (gAECommunity_Type == null) {
            throw new NotFoundException("Could not find GAECommunity_Type with ID: " + id);
        }
        return gAECommunity_Type;
    }

    /**
     * Inserts a new {@code GAECommunity_Type}.
     */
    @ApiMethod(
            name = "insert",
            path = "gAECommunity_Type",
            httpMethod = ApiMethod.HttpMethod.POST)
    public GAECommunity_Type insert(GAECommunity_Type gAECommunity_Type) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that gAECommunity_Type.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(gAECommunity_Type).now();
        logger.info("Created GAECommunity_Type with ID: " + gAECommunity_Type.getId());

        return ofy().load().entity(gAECommunity_Type).now();
    }

    /**
     * Updates an existing {@code GAECommunity_Type}.
     *
     * @param id                the ID of the entity to be updated
     * @param gAECommunity_Type the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAECommunity_Type}
     */
    @ApiMethod(
            name = "update",
            path = "gAECommunity_Type/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public GAECommunity_Type update(@Named("id") long id, GAECommunity_Type gAECommunity_Type) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(gAECommunity_Type).now();
        logger.info("Updated GAECommunity_Type: " + gAECommunity_Type);
        return ofy().load().entity(gAECommunity_Type).now();
    }

    /**
     * Deletes the specified {@code GAECommunity_Type}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAECommunity_Type}
     */
    @ApiMethod(
            name = "remove",
            path = "gAECommunity_Type/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(GAECommunity_Type.class).id(id).now();
        logger.info("Deleted GAECommunity_Type with ID: " + id);
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
            path = "gAECommunity_Type",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<GAECommunity_Type> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<GAECommunity_Type> query = ofy().load().type(GAECommunity_Type.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<GAECommunity_Type> queryIterator = query.iterator();
        List<GAECommunity_Type> gAECommunity_TypeList = new ArrayList<GAECommunity_Type>(limit);
        while (queryIterator.hasNext()) {
            gAECommunity_TypeList.add(queryIterator.next());
        }
        return CollectionResponse.<GAECommunity_Type>builder().setItems(gAECommunity_TypeList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(GAECommunity_Type.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find GAECommunity_Type with ID: " + id);
        }
    }
}