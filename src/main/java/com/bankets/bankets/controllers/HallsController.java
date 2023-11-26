package com.bankets.bankets.controllers;

import com.bankets.bankets.controllers.extraClasses.printHalls;
import com.bankets.bankets.models.*;
import com.bankets.bankets.repo.*;
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
public class HallsController {
    @Autowired
    private BanquetHallsRepository banquetHallsRepository;

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping("/halls")
    public String halls(Model model) throws IOException {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            ArrayList<printHalls> printHallsArrayList = new ArrayList<>();
            ArrayList<String> categoriesArrayList = new ArrayList<>();

            Iterable<BanquetHalls> banquetHalls = banquetHallsRepository.findAll();
            Iterator<BanquetHalls> iterator = banquetHalls.iterator();
            while (iterator.hasNext()) {
                BanquetHalls element = iterator.next();
                printHalls e = new printHalls();

                e.setBanquet_halls_id(element.getBanquet_halls_id());
                e.setCategory_id(element.getCategory_id());
                e.setBanquet_halls_name(element.getBanquet_halls_name());
                e.setDescript(element.getDescript());
                e.setImage_path(element.getImage_path());
                e.setAdress(element.getAdress());
                e.setPrice(element.getPrice());

                Categories categories = categoryRepository.findById(element.getCategory_id()).orElseThrow();
                e.setCategory_name(categories.getCategory_name());
                e.setCategory_description(categories.getCategory_description());

                printHallsArrayList.add(e);
            }
            model.addAttribute("banquetHalls",printHallsArrayList);

            Iterable<Categories> allCategories = categoryRepository.findAll();
            for(Categories category : allCategories) {
                categoriesArrayList.add(category.getCategory_name());
            }
            model.addAttribute("categories",categoriesArrayList);


            return "halls";
        }
        else {return "login";}

    }

    @GetMapping("/halls/add")
    public String hallsAdd(Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            Iterable<Categories> categories = categoryRepository.findAll();
            model.addAttribute("categories",categories);

            return "halls-add";
        }
        else {return "login";}

    }

    @PostMapping("/halls/add")
    public String hallsPostAdd(@RequestParam String name, @RequestParam Long category, @RequestParam String description, @RequestParam("imageFile") MultipartFile  image, @RequestParam String adress, @RequestParam String price, Model model) throws IOException {

        BanquetHalls banquetHalls = new BanquetHalls(category,name,description,adress,price);
        banquetHallsRepository.save(banquetHalls);
        if (!image.isEmpty()) {
            try {
                String fileName = "image" + banquetHalls.getBanquet_halls_id() + ".png";
//                String uploadDir = "E:/Kursach_code/bankets/src/main/resources/static/images/";
                String uploadDir = "E:/Kursach_code/bankets/src/main/resources/templates/images/";
                File saveFile = new File(uploadDir, fileName);
                image.transferTo(saveFile);
                banquetHalls.setImage_path("./images/" + fileName);
                banquetHallsRepository.save(banquetHalls);

            } catch (IOException e) {
                e.printStackTrace();
            }}

        return "redirect:/halls";
    }

    @PostMapping("/halls/{id}/remove")
    public String hallsDelete(@PathVariable(value = "id") long id, Model model) {

        BanquetHalls banquetHalls = banquetHallsRepository.findById(id).orElseThrow();
        if(purchaseItemsRepository.count()>0)
        {
            Long purchaseId = 0L;
            Iterable<PurchaseItems> purchaseItems = purchaseItemsRepository.findAll();
            Iterator<PurchaseItems> iterator1 = purchaseItems.iterator();
            while (iterator1.hasNext()) {
                PurchaseItems purchaseItems1 = iterator1.next();
                if (purchaseItems1.getBanquet_halls_id() == id) {
                    purchaseId = purchaseItems1.getPurchase_id();

                    Purchases purchases = purchasesRepository.findById(purchaseId).orElseThrow();
                    purchasesRepository.delete(purchases);
                    PurchaseItems purchaseItems2 = purchaseItemsRepository.findById(purchaseId).orElseThrow();
                    purchaseItemsRepository.delete(purchaseItems2);
                }
            }


        }
            Iterable<Favorite> favorites = favoriteRepository.findAll();
            Iterator<Favorite> iterator2 = favorites.iterator();
            while (iterator2.hasNext()) {
                Favorite favoriteItem = iterator2.next();
                if (favoriteItem.getId().getBanquet_halls_id().equals(banquetHalls.getBanquet_halls_id())) {
                    favoriteRepository.delete(favoriteItem);
                }
            }
        banquetHallsRepository.delete(banquetHalls);

        File file = new File("E:/Kursach_code/bankets/src/main/resources/templates/images/image" + id + ".png");
        if (file.exists()) {
            if (file.delete());
        }
        return "redirect:/halls";
    }

    @GetMapping("/halls/{id}/change")
    public String hallsChange(@PathVariable(value = "id") Long id, Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            if(!banquetHallsRepository.existsById(id)) {
                return "redirect:/halls";
            }
            Optional<BanquetHalls> banquetHalls = banquetHallsRepository.findById(id);

            ArrayList<BanquetHalls> res = new ArrayList<>();
            banquetHalls.ifPresent(res::add);
            model.addAttribute("banquetHalls", res);

            Iterable<Categories> categories = categoryRepository.findAll();
            model.addAttribute("categories",categories);

            return "halls-change";
        }
        else {return "login";}

    }

    @PostMapping("/halls/{id}/change")
    public String hallsChange (@PathVariable(value = "id") Long id, @RequestParam String name, @RequestParam Long category,@RequestParam("imageFile") MultipartFile  image, @RequestParam String description,@RequestParam String adress,@RequestParam String price, Model model) {

        BanquetHalls banquetHalls = banquetHallsRepository.findById(id).orElseThrow();
        banquetHalls.setBanquet_halls_name(name);
        banquetHalls.setCategory_id(category);
        banquetHalls.setDescript(description);
        banquetHalls.setAdress(adress);
        banquetHalls.setPrice(price);
        banquetHallsRepository.save(banquetHalls);

        if (!image.isEmpty()) {
            try {
                File file = new File("E:/Kursach_code/bankets/src/main/resources/templates/images/image" + id + ".png");
                if (file.exists()) {
                    if (file.delete());
                }

                String fileName = "image" + banquetHalls.getBanquet_halls_id() + ".png";
                String uploadDir = "E:/Kursach_code/bankets/src/main/resources/templates/images/";
                File saveFile = new File(uploadDir, fileName);
                image.transferTo(saveFile);
                banquetHalls.setImage_path("./images/" + fileName);
                banquetHallsRepository.save(banquetHalls);

            } catch (IOException e) {
                e.printStackTrace();
            }}

        return "redirect:/halls";
    }

}
