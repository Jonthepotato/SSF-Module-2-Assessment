package BookSearch.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import BookSearch.Controllers.SearchController;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class BooksList {
    private final static Logger logger = Logger.getLogger(BooksList.class.getName());

    private String title;
    private String key;
    private String description;
    private String excerpts;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpts() {
        return this.excerpts;
    }

    public void setExcerpts(String excerpts) {
        this.excerpts = excerpts;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static List<BooksList> setBooksListData(JsonObject data){
        List<BooksList> masterBookList = new ArrayList<>();
            for (int index = 0; index < 20; index++) {
            BooksList bL = new BooksList();
            bL.setTitle(data.getJsonArray("docs").getJsonObject(index).get("title").toString().replaceAll("^\"+|\"+$", ""));
            bL.setKey(data.getJsonArray("docs").getJsonObject(index).get("key").toString().replace("works","book").replaceAll("^\"+|\"+$", ""));
            // logger.log(Level.INFO, "key: %s".formatted(data.getJsonArray("docs").getJsonObject(index).get("key").toString().replace("works","book").replaceAll("^\"+|\"+$", "")));
            masterBookList.add(bL);
        }
        return masterBookList;
    }

    public static BooksList setBookDetails(JsonObject dataDetails){
        BooksList bookData = new BooksList();
        String excerpt = null;
        String description =null;
        Optional<JsonArray> excerpts = Optional.ofNullable(dataDetails.getJsonArray("excerpts"));
        Optional<JsonObject> descriptions = Optional.ofNullable(dataDetails.getJsonObject("description"));

        if (!descriptions.isEmpty()) {
            bookData.setDescription(dataDetails.getJsonObject("description").toString());
        }
        else{
            bookData.setDescription(description);
        }
        if (!excerpts.isEmpty()) {
            bookData.setExcerpts(dataDetails.getJsonArray("excerpts").getJsonObject(0).get("excerpt").toString());
        }else{
            bookData.setExcerpts(excerpt);
        }
        
        bookData.setTitle(dataDetails.getString("title"));
        // bookData.setDescription(dataDetails.getJsonObject("description").toString());
        // bookData.setExcerpts(dataDetails.getJsonArray("excerpts").getJsonObject(0).get("excerpt").toString());
        // logger.log(Level.INFO, "description: %s".formatted(dataDetails.getJsonObject("description").toString()));
        // logger.log(Level.INFO, "title: %s".formatted(dataDetails.getString("title")));
        // logger.log(Level.INFO, "excerpt: %s".formatted(dataDetails.getJsonArray("excerpts").getJsonObject(0).get("excerpt").toString()));


        // logger.log(Level.INFO, "excerpt: %s".formatted(dataDetails.getJsonArray("excerpts").getJsonObject(0).get("excerpt").toString()));

        return bookData;

    }
    
}
