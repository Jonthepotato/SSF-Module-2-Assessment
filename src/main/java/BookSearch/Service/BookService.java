package BookSearch.Service;

import java.io.IOException;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import BookSearch.Model.BooksList;
import BookSearch.Repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    private final Logger logger = Logger.getLogger(BookService.class.getName());
    public String search(String bookName){
        bookName = bookName.trim().replace(" ", "+");
        String url = UriComponentsBuilder
        .fromUriString("http://openlibrary.org/search.json")
        .queryParam("title",bookName)
        .queryParam("limit",20)
        .toUriString();
        logger.log(Level.INFO, "URL: %s".formatted(url));
        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        String respBody = resp.getBody();
        return respBody;
    }

    public List<BooksList> getReqData(String respBody) throws IOException{
        try(InputStream is = new ByteArrayInputStream(respBody.getBytes())){
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            List<BooksList> masterBooksList = BooksList.setBooksListData(data);
        return masterBooksList;
        }
    }

    public String searchDetails(String key){
        String urlDetails = UriComponentsBuilder
        .fromUriString("https://openlibrary.org/works/"+key+".json")
        .toUriString();
        logger.log(Level.INFO, "URL: for details %s".formatted(urlDetails));
        RequestEntity<Void> reqDetails = RequestEntity.get(urlDetails).build();
        RestTemplate templateDetails = new RestTemplate();
        ResponseEntity<String> respDetails = templateDetails.exchange(reqDetails, String.class);
        String respBodyDetails = respDetails.getBody();
        // logger.log(Level.INFO, "respbodydetails: for details %s".formatted(respBodyDetails));
        return respBodyDetails;
    }

    public BooksList getReqDetailData(String respBodyDetails) throws IOException{
        try(InputStream is = new ByteArrayInputStream(respBodyDetails.getBytes())){
            JsonReader readerDetails = Json.createReader(is);
            JsonObject dataDetails = readerDetails.readObject();
            BooksList bookDetails = BooksList.setBookDetails(dataDetails);
            logger.log(Level.INFO, "bookDetails: for details %s".formatted(bookDetails));
        return bookDetails;

        }

}
    public void save(String key, BooksList bookDetails ){
        bookRepo.save(key, bookDetails);

    }

    public Optional<BooksList> get(String key){
        Optional<BooksList> opt = bookRepo.get(key);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
            else{
                return opt;
            }
            

    }

}
