package BookSearch.Controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import BookSearch.Model.BooksList;
import BookSearch.Service.BookService;

@Controller
@RequestMapping(path="/book", produces = MediaType.TEXT_HTML_VALUE)
public class BookController {
    private final Logger logger = Logger.getLogger(BookController.class.getName());

    @Autowired 
    private BookService bookService;

    @GetMapping("{key}")
    public String clickDetails(@PathVariable String key, Model model) throws IOException{
        logger.log(Level.INFO, "key: %s".formatted(key));
        BooksList bookDetails = null;
        Optional<BooksList> opt = bookService.get(key);

        if (opt.isPresent()) {
             bookDetails = opt.get();
        } else{
            bookDetails = bookService.getReqDetailData(bookService.searchDetails(key));
            bookService.save(key, bookDetails);
                }

        model.addAttribute("bookdet", bookDetails);
        return "bookdetails";
    } 

    
}
