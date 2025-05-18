package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.dto.BookDTO;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from) // .map(book -> BookDTO.BookResponse.from(book))
                .toList();
    }

    public BookDTO.BookResponse getbookById(Long id) {
        Book book = findBookById(id);
        return BookDTO.BookResponse.from(book);
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book Not Found with ISBN: "
                        + isbn, HttpStatus.NOT_FOUND));
        return BookDTO.BookResponse.from(book);
    }

    public List<BookDTO.BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByIsbn(author).stream()
                .map(BookDTO.BookResponse::from)
                .toList();
    }

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        // ISBN 중복 검사
        bookRepository.findByIsbn(request.getIsbn())
                .ifPresent(book -> {
                   throw new BusinessException("Book with this ISBN alread exists", HttpStatus.CONFLICT);
        });
        Book book = request.toEntity();

        Book savedBook = bookRepository.save(book);

        return BookDTO.BookResponse.from(savedBook);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existingBook = findBookById(id);

        if (request.getPrice() != null) {
            existingBook.setPrice(request.getPrice());
        }

        if (request.getTitle() != null) {
            existingBook.setTitle(request.getTitle());
        }

        if (request.getAuthor() != null) {
            existingBook.setAuthor(request.getAuthor());
        }

        if (request.getPublishDate() != null) {
            existingBook.setPublishDate(request.getPublishDate());
        }

        Book updatedBook = bookRepository.save(existingBook);
        return BookDTO.BookResponse.from(updatedBook);
    }

    @Transactional
    void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException("Book Not Found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found with Id: " + id, HttpStatus.NOT_FOUND));
    }
}
