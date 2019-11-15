package utils.document;

import com.mongodb.client.model.Filters;
import data_base_context.DataBaseContext;
import data_base_context.crud_operations.Collection;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DocumentUtils {

    /**
     * Helps to fill and supplement the gaps in JSON document.
     * JSON document must have field 'storage_id' fields with gaps,
     * and this field must have the value in the following format:
     * 'collectionName_jsonDocuemntId'
     * (for example: 'auth_2' - in collection 'auth' with _id 2 there is appropriate document for inserting in gap )
     *
     * Example:
     * Collection 'docs', we have a gap in 'author' filed.
     * By storage_id we can find the appropriate document in collection 'auth' with '_id' 1.
     * {
     *     "_id": "1",
     *     "storage_id": "docs_1",
     *     "author": {
     *       "storage_id": "auth_1"
     *     }
     *   }
     *   Collection 'auth', we have a gap in 'photo' filed.
     *   By storage_id we can find the appropriate document in collection 'photo' with '_id' 1.
     *     {
     *     "_id": 1,
     *     "storage_id": "auth_1",
     *     "name": "Kevin Frowler",
     *     "photo": {
     *       "storage_id": "photo_1"
     *     }
     *   }
     *   Collection 'photo'. We don't have fields with 'storage_id',
     *   and this means that in this document there are no gaps
     *     {
     *     "_id" : 1,
     *     "type" : "JPEG"
     *   }
     *   Result of using this method:
     *   {
     *      *     "_id": "1",
     *      *     "storage_id": "docs_1",
     *      *     "author": {
     *      *           "_id": 1,
     *      *           "storage_id": "auth_1",
     *      *           "name": "Kevin Frowler",
     *      *           "photo": {
     *      *                   "_id" : 1,
     *      *                   "type" : "JPEG"
     *      *     }
     *      *   }
     * @param documentItem - bson Document which must be supplement in case if it has fields with gaps
     */
    public static void checkAndUpdate(Document documentItem) {
        Set<String> fields = documentItem.keySet();
        fields.forEach(field -> {
            Object element = documentItem.get(field);
            if (element instanceof Document) {
                Document docElement = (Document) element;
                if ((docElement).get("storage_id") != null) {
                    String storage_id = String.valueOf((docElement).get("storage_id"));
                    List<String> storageData = Arrays.asList(storage_id.split("_"));
                    Collection collection = new Collection(DataBaseContext.getCollection(System.getProperty("dataBase"), storageData.get(0)));
                    Document document = collection.readDocument(Filters.eq("_id", Integer.parseInt(storageData.get(1))));
                    documentItem.put(field, document);
                    checkAndUpdate(document);
                }
            }
        });
    }
}
