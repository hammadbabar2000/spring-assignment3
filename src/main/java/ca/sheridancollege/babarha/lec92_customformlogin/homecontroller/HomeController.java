package ca.sheridancollege.babarha.lec92_customformlogin.homecontroller;

import ca.sheridancollege.babarha.lec92_customformlogin.beans.Book;
import ca.sheridancollege.babarha.lec92_customformlogin.database.DatabaseAccess;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/secure")
    public String secureIndex(Model model , Authentication authentication) {
        String email = authentication.getName();
        List<String> roleList = new ArrayList<String>();
        for (GrantedAuthority ga: authentication.getAuthorities()) {
            roleList.add(ga.getAuthority());
        }
        model.addAttribute("email", email);
        model.addAttribute("roleList", roleList);
        return "/secure/books";
    }

    @GetMapping("/admin")
    public String adminIndex() {
        return "/admin/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/permission-denied")
    public String permissionDenied() {
        return "/error/permission-denied";
    }

    @GetMapping("/register")
    public String register() { return "/register"; }

    @Autowired
    @Lazy
    private DatabaseAccess da;

    @PostMapping("/register")
    public String postRegister(@RequestParam String username, @RequestParam String password) {
        da.addUser(username, password);
        Long userId = da.findUserAccount(username).getUserId();
        // this next line is dangerous! For extra credit, try making a DatabaseAccess method to find a roleId
        // associate with a role of “ROLE_USER” and add the “correct” id every time
        da.addRole(userId, Long.valueOf(1));

        return "redirect:/login";
    }
    
    // Books stuff

    @GetMapping("/secure/books")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public String books(Model model, Book book) {
        model.addAttribute("bookList", da.getBookList());
        return "/secure/books";
    }

    @PostMapping("/secure/insertBook")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public String insertBook(Model model, Book book) {

        if(book.getId() == null) {
            da.insertBook(book);
        } else {
            da.updateBook(book);
        }

        model.addAttribute("bookList", da.getBookList());
        return "secure/books";
    }

    @GetMapping("/secure/deleteBook/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public String deleteBook(Model model, @PathVariable Long id) {
        da.deleteBook(id);
        return "redirect:/secure/books";
    }

    @GetMapping("/secure/editBook/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public String editBook(Model model, @PathVariable Long id) {

        Book book = da.getBookById(id).get(0);

        model.addAttribute("book", book);
        return "/secure/books";
    }

    @PostMapping("/secure/getBookByTitle")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public String getBookByTitle(Model model, Book book) {

        model.addAttribute("bookList", da.getBookByTitle(book.getTitle()).stream()
                .filter(b -> b.getTitle().toLowerCase().contains(book.getTitle().toLowerCase()))
                .toList());

        return "/secure/books";
    }




}
