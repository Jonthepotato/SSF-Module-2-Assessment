package BookSearch.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import BookSearch.Model.BooksList;

@Repository
public class BookRepository {
    @Autowired 
    private RedisTemplate<String,BooksList> template;

    public void save(String key, BooksList bookDetails) {
        template.opsForValue().set(key, bookDetails, 10, TimeUnit.MINUTES);
    }

    public Optional<BooksList> get(String key){
        Optional<BooksList> value = Optional.ofNullable((BooksList)template.opsForValue().get(key));
        return value;

    }
    
}
