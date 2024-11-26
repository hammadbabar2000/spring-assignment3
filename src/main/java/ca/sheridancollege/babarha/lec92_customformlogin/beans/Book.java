package ca.sheridancollege.babarha.lec92_customformlogin.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {

    private String title;
    private String author;
    private String publisher;
    private Long price;
    private Long id;
    private String status;

}

