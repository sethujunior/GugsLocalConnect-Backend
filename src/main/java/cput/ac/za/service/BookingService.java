package cput.ac.za.service;

import cput.ac.za.domain.Booking;
import cput.ac.za.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBooking{

    private BookingRepository bookingRepository;
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking create(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking read(Long Id) {
        return bookingRepository.findById(Id).orElse(null);
    }

    @Override
    public Booking update(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public boolean delete(Long Id) {
        if (bookingRepository.existsById(Id)) {
            bookingRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }
}
