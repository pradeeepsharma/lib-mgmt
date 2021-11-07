package com.learning.LibraryManagementSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.LibraryManagementSystem.models.*;
import com.learning.LibraryManagementSystem.repository.BillingRepository;
import com.learning.LibraryManagementSystem.repository.BookRepository;
import com.learning.LibraryManagementSystem.repository.BorrowBookRepository;
import com.learning.LibraryManagementSystem.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectTests {

    private MockMvc mockMvc;


    @Autowired
    WebApplicationContext context;

    @Autowired
    private BookRepository br;

    @Autowired
    private UserRepository ur;
    @Autowired
    private BillingRepository billr;
    @Autowired
    private BorrowBookRepository bbr;
    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void addUser_test() throws Exception{
        User u=new User();
        u.setUserid(1l);
        u.setUserName("jhon");
        u.setEmailId("jhon@tcs.com");
        byte[] iJson = toJson(u);
        mockMvc.perform(post("/user")
                .content(iJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        User u1=new User();
        u1.setUserid(2l);
        u1.setUserName("jhon1");
        u1.setEmailId("jhon1@tcs.com");
        byte[] iJson1 = toJson(u1);
        mockMvc.perform(post("/user")
                .content(iJson1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/user/1" )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userid").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("jhon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailId").value("jhon@tcs.com"));
        mockMvc.perform(get("/user/2" )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userid").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("jhon1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailId").value("jhon1@tcs.com"));
        List<User> userList = new ArrayList<>();
        ur.findAll().forEach(userList::add);
        assertEquals(6,userList.size() );
        mockMvc.perform(delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        userList = new ArrayList<>();
        ur.findAll().forEach(userList::add);
        assertEquals(5,userList.size() );


    }
    @Test
    public void addBook_test() throws Exception{
        Book b = new Book(1l,"microservices","microservices with springboot","http://book.com",5,"available",null);
        byte[] iJson = toJson(b);
        mockMvc.perform(post("/books")
                .content(iJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        Book b1 = new Book(2l,"microservices1","microservices with spring5.0","http://spring.com",5,"available",null);
        byte[] iJson1 = toJson(b1);
        mockMvc.perform(post("/books")
                .content(iJson1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/books/1" )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("microservices"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName").value("microservices with springboot"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookUrl").value("http://book.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.availableBooks").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("available"));
        mockMvc.perform(get("/books/2" )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("microservices1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName").value("microservices with spring5.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookUrl").value("http://spring.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.availableBooks").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("available"));
        List<Book> books = new ArrayList<>();
        br.findAll().forEach(books::add);
        assertEquals(6,books.size());
        mockMvc.perform(delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        books = new ArrayList<>();
        br.findAll().forEach(books::add);
        assertEquals(5,books.size());
    }

    @Test
    public void borrowBook_test() throws Exception{
        List<BorrowBook> bb =new ArrayList<BorrowBook>();
        List<BorrowBook> bb1 =new ArrayList<BorrowBook>();

        User u=new User(1,"jhon","jhon@tcs.com",bb);

        byte[] iJson = toJson(u);
        mockMvc.perform(post("/user")
                .content(iJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        User u1=new User(2,"jhon1","jhon1@tcs.com",bb1);

        byte[] iJson1 = toJson(u1);
        mockMvc.perform(post("/user")
                .content(iJson1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/user/1" )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userid").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("jhon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailId").value("jhon@tcs.com"));
        mockMvc.perform(get("/user/2" )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userid").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("jhon1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailId").value("jhon1@tcs.com"));
       // assertEquals(2,ur.findAll().size() );
        Book b = new Book(1l,"microservices","microservices with springboot","http://book.com",5,"available",null);
        byte[] iJson2 = toJson(b);
        mockMvc.perform(post("/books")
                .content(iJson2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        Book b1 = new Book(2l,"microservices1","microservices with spring5.0","http://spring.com",5,"available",null);
        byte[] iJson3 = toJson(b1);
        mockMvc.perform(post("/books")
                .content(iJson3)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/books/1" )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("microservices"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName").value("microservices with springboot"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookUrl").value("http://book.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.availableBooks").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("available"));
        mockMvc.perform(get("/books/2" )).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("microservices1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName").value("microservices with spring5.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookUrl").value("http://spring.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.availableBooks").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("available"));
       // assertEquals(2,br.findAll().size());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);


        BorrowBook bb3 = new BorrowBook(1l,2l,1l,calendar.getTime(),50.0,4,null);

        byte[] iJson4 = toJson(bb3);
        mockMvc.perform(post("/borrowbook/")
                .content(iJson4)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/borrowbook/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookId").value(1))

                .andExpect(MockMvcResultMatchers.jsonPath("$.chargePerDay").value(50.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.noOfDays").value("4"));
        bbr.save(bb3);

        mockMvc.perform(post("/billing/1")
            //    .content(ijson5)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/billing/getbill/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowBook.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowBook.userId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowBook.chargePerDay").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowBook.bookId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowBook.noOfDays").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.finePerDay").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.billAmount").value(260));





    }





    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
