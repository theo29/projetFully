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
        name = "gAEChatCallApi",
        version = "v1",
        resource = "gAEChatCall",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.TheoP.example.com",
                ownerName = "backend.myapplication.TheoP.example.com",
                packagePath = ""
        )
)
public class GAEChatCallEndpoint {

    private static final Logger logger = Logger.getLogger(GAEChatCallEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(GAEChatCall.class);
    }

    /**
     * Returns the {@link GAEChatCall} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code GAEChatCall} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "gAEChatCall/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public GAEChatCall get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting GAEChatCall with ID: " + id);
        GAEChatCall gAEChatCall = ofy().load().type(GAEChatCall.class).id(id).now();
        if (gAEChatCall == null) {
            throw new NotFoundException("Could not find GAEChatCall with ID: " + id);
        }
        return gAEChatCall;
    }

    /**
     * Inserts a new {@code GAEChatCall}.
     */
    @ApiMethod(
            name = "insert",
            path = "gAEChatCall",
            httpMethod = ApiMethod.HttpMethod.POST)
    public GAEChatCall insert(GAEChatCall gAEChatCall) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that gAEChatCall.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(gAEChatCall).now();
        logger.info("Created GAEChatCall.");

        return ofy().load().entity(gAEChatCall).now();
    }

    /**
     * Updates an existing {@code GAEChatCall}.
     *
     * @param id          the ID of the entity to be updated
     * @param gAEChatCall the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAEChatCall}
     */
    @ApiMethod(
            name = "update",
            path = "gAEChatCall/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public GAEChatCall update(@Named("id") long id, GAEChatCall gAEChatCall) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(gAEChatCall).now();
        logger.info("Updated GAEChatCall: " + gAEChatCall);
        return ofy().load().entity(gAEChatCall).now();
    }

    /**
     * Deletes the specified {@code GAEChatCall}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAEChatCall}
     */
    @ApiMethod(
            name = "remove",
            path = "gAEChatCall/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(GAEChatCall.class).id(id).now();
        logger.info("Deleted GAEChatCall with ID: " + id);
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
            path = "gAEChatCall",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<GAEChatCall> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<GAEChatCall> query = ofy().load().type(GAEChatCall.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<GAEChatCall> queryIterator = query.iterator();
        List<GAEChatCall> gAEChatCallList = new ArrayList<GAEChatCall>(limit);
        while (queryIterator.hasNext()) {
            gAEChatCallList.add(queryIterator.next());
        }
        return CollectionResponse.<GAEChatCall>builder().setItems(gAEChatCallList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(GAEChatCall.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find GAEChatCall with ID: " + id);
        }
    }
}