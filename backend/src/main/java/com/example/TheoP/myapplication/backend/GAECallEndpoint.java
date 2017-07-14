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
        name = "gAECallApi",
        version = "v1",
        resource = "gAECall",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.TheoP.example.com",
                ownerName = "backend.myapplication.TheoP.example.com",
                packagePath = ""
        )
)
public class GAECallEndpoint {

    private static final Logger logger = Logger.getLogger(GAECallEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(GAECall.class);
    }

    /**
     * Returns the {@link GAECall} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code GAECall} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "gAECall/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public GAECall get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting GAECall with ID: " + id);
        GAECall gAECall = ofy().load().type(GAECall.class).id(id).now();
        if (gAECall == null) {
            throw new NotFoundException("Could not find GAECall with ID: " + id);
        }
        return gAECall;
    }

    /**
     * Inserts a new {@code GAECall}.
     */
    @ApiMethod(
            name = "insert",
            path = "gAECall",
            httpMethod = ApiMethod.HttpMethod.POST)
    public GAECall insert(GAECall gAECall) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that gAECall.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(gAECall).now();
        logger.info("Created GAECall with ID: " + gAECall.getId());

        return ofy().load().entity(gAECall).now();
    }

    /**
     * Updates an existing {@code GAECall}.
     *
     * @param id      the ID of the entity to be updated
     * @param gAECall the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAECall}
     */
    @ApiMethod(
            name = "update",
            path = "gAECall/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public GAECall update(@Named("id") long id, GAECall gAECall) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(gAECall).now();
        logger.info("Updated GAECall: " + gAECall);
        return ofy().load().entity(gAECall).now();
    }

    /**
     * Deletes the specified {@code GAECall}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAECall}
     */
    @ApiMethod(
            name = "remove",
            path = "gAECall/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(GAECall.class).id(id).now();
        logger.info("Deleted GAECall with ID: " + id);
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
            path = "gAECall",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<GAECall> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<GAECall> query = ofy().load().type(GAECall.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<GAECall> queryIterator = query.iterator();
        List<GAECall> gAECallList = new ArrayList<GAECall>(limit);
        while (queryIterator.hasNext()) {
            gAECallList.add(queryIterator.next());
        }
        return CollectionResponse.<GAECall>builder().setItems(gAECallList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(GAECall.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find GAECall with ID: " + id);
        }
    }
}