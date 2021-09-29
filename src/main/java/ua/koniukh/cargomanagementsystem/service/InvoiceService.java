package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Invoice;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.repository.InvoiceRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserService userService;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, UserService userService) {
        this.invoiceRepository = invoiceRepository;
        this.userService = userService;
    }

    public Invoice findById(Long id) {
        return invoiceRepository.getById(id);
    }

//    public Invoice findByOrderId(Long id) {
//        List<Invoice> list = new ArrayList<>(invoiceRepository.findAll());
//        Invoice resultInvoice = new Invoice();
//        for (Invoice invoice : list) {
//            if (invoice.getOrder().getId() == id) {
//                resultInvoice = invoice;
//            }
//        }
//        return resultInvoice;
//    }

//    public List<Invoice> findByUserId(Long id) {
//        List<Invoice> list = new ArrayList<>(invoiceRepository.findAll());
//        List<Invoice> resultList = new ArrayList<>();
//        for (Invoice invoice : list) {
//            if (invoice.getUser().getId() == id) {
//                resultList.add(invoice);
//            }
//        }
//        return resultList;
//    }

    public List<Invoice> getUserInvoiceListByPaidStatus(Boolean bool, User user) {
        if(bool == true) {
            List<Invoice> paidInvoiceList = invoiceRepository.findAllByPaidAndUserId(true, user.getId());
            return paidInvoiceList;
        } List<Invoice> unpaidInvoiceList = invoiceRepository.findAllByPaidAndUserId(false, user.getId());
        return unpaidInvoiceList;
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice createInvoice(@NotNull Order order) {
        Invoice invoice = Invoice.builder()
                .description(order.getType())
                .price(order.getPrice())
                .user(order.getUser())
                .order(order)
                .build();

        saveInvoice(invoice);
        return invoice;
    }
//
//    public List<Invoice> getCurrentUserInvoiceList(Authentication authentication) {
//        User user = userService.getCurrentUser(authentication);
//        return user.getInvoices();
//    }

    @Transactional
    public void payInvoice(Long id) {
        Invoice invoice = findById(id);
        invoice.setPaid(true);
        saveInvoice(invoice);
    }
}
