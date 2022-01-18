package BookSearch.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import BookSearch.Model.BooksList;
import BookSearch.Service.BookService;

@Controller
@RequestMapping(path= "/", produces = MediaType.TEXT_HTML_VALUE)
public class SearchController {
    private final Logger logger = Logger.getLogger(SearchController.class.getName());
    @Autowired
    private BookService bookService;

    @GetMapping
    public String showForm(){
        return "index";
    }

    @GetMapping("{book}")
    public String filledForm(@RequestParam String bookName, Model model) throws IOException{
        logger.log(Level.INFO, "bookName: %s".formatted(bookName));
        List<BooksList> masterBooksList = bookService.getReqData(bookService.search(bookName));
        model.addAttribute("books", masterBooksList);
        if (masterBooksList.size()<1)
            return "error";

            else
            
                return "booklist";
    }
    
}
