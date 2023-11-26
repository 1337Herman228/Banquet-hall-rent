package com.bankets.bankets.controllers;

import com.bankets.bankets.controllers.extraClasses.printHalls;
import com.bankets.bankets.models.*;
import com.bankets.bankets.repo.*;
import com.bankets.bankets.service.BanquetHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private BanquetHallService banquetHallService;

    @Autowired
    private BanquetHallsRepository banquetHallsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/banquet-halls")
    public String home(Model model, @RequestParam(defaultValue = "0") int page) {

        if (AuthorizationController.authorizedUser.isAuthorized()) {

            int pageSize = 8;
            Long totalHalls = banquetHallService.getTotalHalls();
            int totalPages = (int) Math.ceil((double) totalHalls / pageSize);
            List<printHalls> halls = banquetHallService.getBanquetHalls(page, pageSize);

            List<Long> allFavoriteHalls = banquetHallService.getAllFavoriteBanquetHallsId(AuthorizationController.authorizedUser.getUserId());
            model.addAttribute("allFavoriteHalls", allFavoriteHalls);

            Iterable<Categories> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);

            model.addAttribute("banquetHalls", halls);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);

            int minPrice = Integer.parseInt(halls.get(0).getPrice());
            int maxPrice = Integer.parseInt(halls.get(0).getPrice());
            for (int i = 0; i < halls.size(); i++) {
                if (Integer.parseInt(halls.get(i).getPrice()) < minPrice) {
                    minPrice = Integer.parseInt(halls.get(i).getPrice());
                }
                if (Integer.parseInt(halls.get(i).getPrice()) > maxPrice) {
                    maxPrice = Integer.parseInt(halls.get(i).getPrice());
                }
            }
            model.addAttribute("minPrice", minPrice);
            model.addAttribute("maxPrice", maxPrice);

            return "home";
        } else {
            return "login";
        }
    }

    @GetMapping("/banquet-halls-admin")
    public String homeAdmin(Model model, @RequestParam(defaultValue = "0") int page) {

        if (AuthorizationController.authorizedUser.isAuthorized() && AuthorizationController.authorizedUser.getRole().equals("Администратор")) {

            int pageSize = 8;
            Long totalHalls = banquetHallService.getTotalHalls();
            int totalPages = (int) Math.ceil((double) totalHalls / pageSize);
            List<printHalls> halls = banquetHallService.getBanquetHalls(page, pageSize);

            List<Long> allFavoriteHalls = banquetHallService.getAllFavoriteBanquetHallsId(AuthorizationController.authorizedUser.getUserId());
            model.addAttribute("allFavoriteHalls", allFavoriteHalls);

            Iterable<Categories> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);

            model.addAttribute("banquetHalls", halls);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);

            int minPrice = Integer.parseInt(halls.get(0).getPrice());
            int maxPrice = Integer.parseInt(halls.get(0).getPrice());
            for (int i = 0; i < halls.size(); i++) {
                if (Integer.parseInt(halls.get(i).getPrice()) < minPrice) {
                    minPrice = Integer.parseInt(halls.get(i).getPrice());
                }
                if (Integer.parseInt(halls.get(i).getPrice()) > maxPrice) {
                    maxPrice = Integer.parseInt(halls.get(i).getPrice());
                }
            }
            model.addAttribute("minPrice", minPrice);
            model.addAttribute("maxPrice", maxPrice);

            return "home-admin";
        } else {
            return "login";
        }
    }

    @PostMapping("/banquet-halls/{id}")
    public String addToFavorite(@PathVariable(value = "id") long id, Model model) {

        CompositeKey idKey = new CompositeKey(AuthorizationController.authorizedUser.getUserId(), id);
        Favorite favorite = new Favorite(idKey);
        favoriteRepository.save(favorite);

        return "home";
    }

    @PostMapping("/banquet-halls/{id}/remove-from-favorite")
    public String removeFromFavorite(@PathVariable(value = "id") long id, Model model) {

        List<Favorite> favorite = favoriteRepository.findByCompositeKey(AuthorizationController.authorizedUser.getUserId(), id);
        for (int i = 0; i < favorite.size(); i++) {
            favoriteRepository.delete(favorite.get(i));
        }

        return "home";
    }

    @GetMapping("/about")
    //@RequestParam(name="name", required=false, defaultValue="World") String name,
    public String about(Model model) {

        if (AuthorizationController.authorizedUser.isAuthorized()) {
            model.addAttribute("title", "Страница про нас");
            return "about";
        } else {
            return "login";
        }
    }

    @GetMapping("/favorite")
    public String favorite(Model model, @RequestParam(defaultValue = "0") int page) {

        if (AuthorizationController.authorizedUser.isAuthorized()) {

            int pageSize = 8;
            Long totalHalls = banquetHallService.getTotalFavoriteHalls(AuthorizationController.authorizedUser.getUserId());

            int totalPages = (int) Math.ceil((double) totalHalls / pageSize);
            List<printHalls> halls = banquetHallService.getFavoriteBanquetHalls(page, pageSize, AuthorizationController.authorizedUser.getUserId());

            List<Long> allFavoriteHalls = banquetHallService.getAllFavoriteBanquetHallsId(AuthorizationController.authorizedUser.getUserId());
            model.addAttribute("allFavoriteHalls", allFavoriteHalls);

            model.addAttribute("banquetHalls", halls);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);

            return "favorite";
        } else {
            return "login";
        }
    }

    @GetMapping("/my-orders")
    public String myOrders(Model model) {
        if (AuthorizationController.authorizedUser.isAuthorized()) {

            List<printHalls> halls = new ArrayList<>();

            Iterable<Purchases> purchases = purchasesRepository.findAll();
            Iterator<Purchases> iterator = purchases.iterator();
            while (iterator.hasNext()) {
                Purchases item = iterator.next();

                if(item.getCustomer_id().equals(AuthorizationController.authorizedUser.getUserId())) {
                    PurchaseItems purchaseItems = purchaseItemsRepository.findById(item.getPurchase_id()).orElseThrow();
                    BanquetHalls banquetHalls = banquetHallsRepository.findById(purchaseItems.getBanquet_halls_id()).orElseThrow();
                    Categories categories = categoryRepository.findById(banquetHalls.getCategory_id()).orElseThrow();

                    printHalls e = new printHalls();
                    e.setBanquet_halls_id(banquetHalls.getBanquet_halls_id());
                    e.setBanquet_halls_name(banquetHalls.getBanquet_halls_name());
                    e.setCategory_name(categories.getCategory_name());
                    e.setPrice(banquetHalls.getPrice());
                    e.setAdress(banquetHalls.getAdress());
                    e.setPurchase_id(item.getPurchase_id());

                    String[] dateParts = purchaseItems.getPurchase_date().toString().replaceFirst("00:00:00.0","").replaceFirst(" ","").split("-");
                    String date = dateParts[2] + "." + dateParts[1] + "." + dateParts[0];

                    e.setDate(date);

                    halls.add(e);
                }
            }
            model.addAttribute("banquetHalls", halls);

            Users users = usersRepository.findById(AuthorizationController.authorizedUser.getUserId()).orElseThrow();
            Person person = personRepository.findById(users.getPerson_id()).orElseThrow();
            model.addAttribute("userId", users.getCustomer_id());
            model.addAttribute("login", users.getLogin());
            model.addAttribute("fullName", person.getName());
            model.addAttribute("phone", person.getPhone());
            model.addAttribute("email", person.getEmail());

            return "my-orders";
        } else {
            return "login";
        }
    }

    @PostMapping("/my-orders/del-order/{id}")
    public String deleteOrder(@PathVariable(value = "id") long id, Model model) {

        Purchases purchases = purchasesRepository.findById(id).orElseThrow();
        purchasesRepository.delete(purchases);
        PurchaseItems purchaseItems = purchaseItemsRepository.findById(id).orElseThrow();
        purchaseItemsRepository.delete(purchaseItems);

        return "redirect:/my-orders";
    }

}
