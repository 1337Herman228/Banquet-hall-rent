package com.bankets.bankets.controllers;

import com.bankets.bankets.controllers.extraClasses.printHalls;
import com.bankets.bankets.models.*;
import com.bankets.bankets.repo.BanquetHallsRepository;
import com.bankets.bankets.repo.CategoryRepository;
import com.bankets.bankets.repo.PurchaseItemsRepository;
import com.bankets.bankets.repo.PurchasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class ViewBanquetHallController {

    @Autowired
    private BanquetHallsRepository banquetHallsRepository;

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/banquet-hall-view/{id}")
    public String banquetHallView(@PathVariable(value = "id") Long id, Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized()) {

            BanquetHalls banquetHalls = banquetHallsRepository.findById(id).orElseThrow();
            Categories categories = categoryRepository.findById(banquetHalls.getCategory_id()).orElseThrow();

            ArrayList<String> dateArrayList = new ArrayList<>();

            Iterable<PurchaseItems> purchaseItems = purchaseItemsRepository.findAll();
            Iterator<PurchaseItems> iterator1 = purchaseItems.iterator();
            while (iterator1.hasNext()) {
                PurchaseItems purchaseItems1 = iterator1.next();
                if (purchaseItems1.getBanquet_halls_id().equals(banquetHalls.getBanquet_halls_id())) {
                    dateArrayList.add(purchaseItems1.getPurchase_date().toString().replaceFirst("00:00:00.0","").replaceFirst(" ",""));
                }
            }

            model.addAttribute("banquetHalls", banquetHalls);
            model.addAttribute("bh_id", banquetHalls.getBanquet_halls_id());
            model.addAttribute("category", categories);
            model.addAttribute("dateArrayList", dateArrayList);

            return "banquet-hall-view";
        }
        else {return "login";}

    }

    @GetMapping("/banquet-hall-view-admin/{id}")
    public String banquetHallViewAdmin(@PathVariable(value = "id") Long id, Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            BanquetHalls banquetHalls = banquetHallsRepository.findById(id).orElseThrow();
            Categories categories = categoryRepository.findById(banquetHalls.getCategory_id()).orElseThrow();

            ArrayList<String> dateArrayList = new ArrayList<>();

            Iterable<PurchaseItems> purchaseItems = purchaseItemsRepository.findAll();
            Iterator<PurchaseItems> iterator1 = purchaseItems.iterator();
            while (iterator1.hasNext()) {
                PurchaseItems purchaseItems1 = iterator1.next();
                if (purchaseItems1.getBanquet_halls_id().equals(banquetHalls.getBanquet_halls_id())) {
                    dateArrayList.add(purchaseItems1.getPurchase_date().toString().replaceFirst("00:00:00.0","").replaceFirst(" ",""));
                }
            }

            model.addAttribute("banquetHalls", banquetHalls);
            model.addAttribute("bh_id", banquetHalls.getBanquet_halls_id());
            model.addAttribute("category", categories);
            model.addAttribute("dateArrayList", dateArrayList);

            return "banquet-hall-view-admin";
        }
        else {return "login";}

    }

}
