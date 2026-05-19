package com.example.application.views.transaktion;

import com.example.application.domain.Account;
import com.example.application.domain.AccountException;
import com.example.application.service.BankService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.LocalDate;

@PageTitle("Transaktion")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.ALIGN_CENTER_SOLID)
public class TransaktionView extends VerticalLayout {
    private final Button removeAll = new Button("Remove all");
    private final Button add10Accounts = new Button("Add 10 accounts");
    private final Button addWrongAccount = new Button("Add wrong accounts");
    private final Button raiseBalance = new Button("Raise balance");


    private final Grid<Account> grid = new Grid<>(Account.class, false);
    private final BankService bankService;

    public TransaktionView(@Autowired BankService bankService) {
        this.bankService = bankService;
        setSpacing(true);

        grid.addColumn(a -> a.getAccountId())
                .setHeader("AccountId")
                .setSortable(true);
        grid.addColumn(a -> a.getFirstName())
                .setHeader("First Name")
                .setSortable(true);
        Image imgCalender = new Image("icons/kalender.png", "kalender");
        imgCalender.setWidth("25px");
        grid.addColumn(a -> a.getOpeningDate())
                .setHeader(new HorizontalLayout(imgCalender, new Span("Date")))
                .setSortable(true);
        grid.addColumn(a -> a.getAccountType())
                .setHeader("Account Type")
                .setSortable(true);
        Image imgEurope = new Image("icons/euro.png", "euro");
        imgEurope.setWidth("25px");
        grid.addColumn(a -> String.format("%.2f", a.getBalance()))
                .setHeader(new HorizontalLayout(imgEurope, new Span("Balance")))
                .setSortable(true);
        grid.addColumn(a -> a.getActive())
                .setHeader("Active")
                .setSortable(true);


        setSizeFull();
        grid.setSizeFull();
        removeAll.addClickListener(e -> removeAllAccounts());
        add10Accounts.addClickListener(e -> add10Accounts());
        addWrongAccount.addClickListener(e -> addWrongAccount());
        raiseBalance.addClickListener(e -> raiseBalance());
        HorizontalLayout buttons = new HorizontalLayout(removeAll, add10Accounts,addWrongAccount,raiseBalance);
        add(buttons, grid);
        reload();

    }

    private void raiseBalance() {
        try {
            bankService.raise();
            reload();
        }catch (AccountException e){
            Notification.show(e.getMessage());
        }
    }

    private void addWrongAccount() {
        try {
            Account a = new Account("Fritz", LocalDate.now(), "Savings", -2000.0, true);
            bankService.addAccount(a);
            reload();
        }catch (AccountException e) {
            Notification.show(e.getMessage());
        }
    }

    private void add10Accounts() {
        try {
            bankService.fillTestData(10);
            removeAll.setEnabled(true);
            reload();
        }catch (AccountException e) {
            Notification.show(e.getMessage());
        }
    }

    private void removeAllAccounts() {
        try {
            bankService.removeAllAccounts();
            removeAll.setEnabled(false);
            reload();
        }catch (AccountException e) {
            Notification.show(e.getMessage());
        }
    }

    private void reload() {
        grid.setItems(bankService.findAll());
    }

}
