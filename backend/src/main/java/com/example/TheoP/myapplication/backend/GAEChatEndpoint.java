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
        name = "gAEChatApi",
        version = "v1",
        resource = "gAEChat",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.TheoP.example.com",
                ownerName = "backend.myapplication.TheoP.example.com",
                packagePath = ""
        )
)
public class GAEChatEndpoint {

    private static final Logger logger = Logger.getLogger(GAEChatEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(GAEChat.class);
    }

    /**
     * Returns the {@link GAEChat} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code GAEChat} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "gAEChat/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public GAEChat get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting GAEChat with ID: " + id);
        GAEChat gAEChat = ofy().load().type(GAEChat.class).id(id).now();
        if (gAEChat == null) {
            throw new NotFoundException("Could not find GAEChat with ID: " + id);
        }
        return gAEChat;
    }

    /**
     * Inserts a new {@code GAEChat}.
     */
    @ApiMethod(
            name = "insert",
            path = "gAEChat",
            httpMethod = ApiMethod.HttpMethod.POST)
    public GAEChat insert(GAEChat gAEChat) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that gAEChat.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. Long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(gAEChat).now();
        logger.info("Created GAEChat.");

        return ofy().load().entity(gAEChat).now();
    }

    /**
     * Updates an existing {@code GAEChat}.
     *
     * @param id      the ID of the entity to be updated
     * @param gAEChat the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAEChat}
     */
    @ApiMethod(
            name = "update",
            path = "gAEChat/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public GAEChat update(@Named("id") Long id, GAEChat gAEChat) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(gAEChat).now();
        logger.info("Updated GAEChat: " + gAEChat);
        return ofy().load().entity(gAEChat).now();
    }

    /**
     * Deletes the specified {@code GAEChat}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code GAEChat}
     */
    @ApiMethod(
            name = "remove",
            path = "gAEChat/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(GAEChat.class).id(id).now();
        logger.info("Deleted GAEChat with ID: " + id);
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
            path = "gAEChat",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<GAEChat> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<GAEChat> query = ofy().load().type(GAEChat.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<GAEChat> queryIterator = query.iterator();
        List<GAEChat> gAEChatList = new ArrayList<GAEChat>(limit);
        while (queryIterator.hasNext()) {
            gAEChatList.add(queryIterator.next());
        }
        return CollectionResponse.<GAEChat>builder().setItems(gAEChatList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(GAEChat.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find GAEChat with ID: " + id);
        }
    }
}