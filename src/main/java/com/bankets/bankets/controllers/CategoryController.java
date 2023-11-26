package com.bankets.bankets.controllers;

import com.bankets.bankets.models.*;
import com.bankets.bankets.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BanquetHallsRepository banquetHallsRepository;

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping("/category")
    public String categoryMain(Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            Iterable<Categories> categories = categoryRepository.findAll();
            model.addAttribute("categories",categories);

            return "category";
        }
        else {return "login";}
    }

    @GetMapping("/category/add")
    public String categoryAdd(Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            return "category-add";
        }
        else {return "login";}
    }

    @PostMapping("/category/add")
    public String categoryAdd(@RequestParam String title,@RequestParam String descript, Model model) {
        Categories categories = new Categories(title,descript);
        categoryRepository.save(categories);
        return "redirect:/category";
    }

    @PostMapping("/category/{id}/remove")
    public String categoryDelete(@PathVariable(value = "id") long id, Model model) {

        Iterable<BanquetHalls> banquetHalls = banquetHallsRepository.findAll();
        Iterator<BanquetHalls> iterator = banquetHalls.iterator();
        while (iterator.hasNext()) {
            BanquetHalls element = iterator.next();
            if(element.getCategory_id() == id) {

                Long purchaseId = 0L;
                Iterable<PurchaseItems> purchaseItems = purchaseItemsRepository.findAll();
                Iterator<PurchaseItems> iterator1 = purchaseItems.iterator();
                while (iterator1.hasNext()) {
                    PurchaseItems purchaseItems1 = iterator1.next();
                    if (purchaseItems1.getBanquet_halls_id() == element.getBanquet_halls_id()) {
                        purchaseId = purchaseItems1.getPurchase_id();

                        Purchases purchases = purchasesRepository.findById(purchaseId).orElseThrow();
                        purchasesRepository.delete(purchases);
                        PurchaseItems purchaseItems2 = purchaseItemsRepository.findById(purchaseId).orElseThrow();
                        purchaseItemsRepository.delete(purchaseItems2);

                    }
                }
                Iterable<Favorite> favorites = favoriteRepository.findAll();
                Iterator<Favorite> iterator2 = favorites.iterator();
                while (iterator2.hasNext()) {
                    Favorite favoriteItem = iterator2.next();
                    if (favoriteItem.getId().getBanquet_halls_id().equals(element.getBanquet_halls_id())) {
                        favoriteRepository.delete(favoriteItem);
                    }

                }

                File file = new File("E:/Kursach_code/bankets/src/main/resources/templates/images/image" + element.getBanquet_halls_id() + ".png");
                if (file.exists()) {
                    if (file.delete());
                }

                banquetHallsRepository.delete(element);
            }
        }
        Categories categories = categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(categories);

        return "redirect:/category";
    }

    @GetMapping("/category/{id}/change")
    public String categoryChange(@PathVariable(value = "id") Long id, Model model) {

        if(AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            if(!categoryRepository.existsById(id)) {
                return "redirect:/category";
            }
            Optional<Categories> categories = categoryRepository.findById(id);
            ArrayList<Categories> res = new ArrayList<>();
            categories.ifPresent(res::add);
            model.addAttribute("categories", res);

            return "category-change";
        }
        else {return "login";}
    }

    @PostMapping("/category/{id}/change")
    public String categoryChange (@PathVariable(value = "id") Long id, @RequestParam String category_name, @RequestParam String category_description, Model model) {

        Categories categories = categoryRepository.findById(id).orElseThrow();
        categories.setCategory_name(category_name);
        categories.setCategory_description(category_description);
        categoryRepository.save(categories);

        return "redirect:/category";
    }
}
