package ua.koniukh.cargomanagementsystem.service;

import com.sun.istack.NotNull;
import ua.koniukh.cargomanagementsystem.model.Invoice;
import ua.koniukh.cargomanagementsystem.model.Order;
import ua.koniukh.cargomanagementsystem.model.User;

import java.util.List;

public interface InvoiceService {

    Invoice findById(Long id);

    List<Invoice> getUserInvoiceListByPaidStatus(Boolean bool, User user);

    Invoice saveInvoice(Invoice invoice);

    Invoice createInvoice(@NotNull Order order);

    void payInvoice(Long id);
}
