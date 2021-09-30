package ua.koniukh.cargomanagementsystem.service.impl;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.koniukh.cargomanagementsystem.model.Invoice;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.User;
import ua.koniukh.cargomanagementsystem.repository.InvoiceRepository;
import ua.koniukh.cargomanagementsystem.service.InvoiceService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.getById(id);
    }

    @Override
    public List<Invoice> getUserInvoiceListByPaidStatus(Boolean bool, User user) {
        if(bool == true) {
            List<Invoice> paidInvoiceList = invoiceRepository.findAllByPaidAndUserId(true, user.getId());
            return paidInvoiceList;
        } List<Invoice> unpaidInvoiceList = invoiceRepository.findAllByPaidAndUserId(false, user.getId());
        return unpaidInvoiceList;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
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

    @Override
    @Transactional
    public void payInvoice(Long id) {
        Invoice invoice = findById(id);
        invoice.setPaid(true);
        saveInvoice(invoice);
    }
}
