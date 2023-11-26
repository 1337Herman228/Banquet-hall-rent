package com.bankets.bankets.service;

import com.bankets.bankets.controllers.extraClasses.printHalls;
import com.bankets.bankets.models.BanquetHalls;
import com.bankets.bankets.models.Favorite;
import com.bankets.bankets.repo.BanquetHallsRepository;
import com.bankets.bankets.repo.CategoryRepository;
import com.bankets.bankets.repo.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bankets.bankets.models.Categories;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BanquetHallService {

    @Autowired
    private BanquetHallsRepository banquetHallsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<printHalls> getBanquetHalls(int page, int pageSize) {

        ArrayList<printHalls> printHallsArrayList = new ArrayList<>();

        Iterable<BanquetHalls> banquetHalls = banquetHallsRepository.findAll();
        Iterator<BanquetHalls> iterator = banquetHalls.iterator();

        int i = page * pageSize;
        for (int j = 0; j < i; j++) {
            iterator.next();
        }

        while (iterator.hasNext() && i >= page * pageSize && i < page * pageSize + pageSize) {
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
            i++;
        }

        return printHallsArrayList;

        // Реализуйте логику получения банкетных залов с учетом пагинации
        // Например, используя репозиторий и методы, такие как findAll(Pageable)
        // Верните только нужные 20 залов для указанной страницы
    }

    public Long getTotalHalls() {

        return banquetHallsRepository.count();

    }

    public Long getTotalFavoriteHalls(Long customerId) {

        Long count = 0L;

        Iterable<Favorite> favorites = favoriteRepository.findAll();
        Iterator<Favorite> iterator = favorites.iterator();
        while (iterator.hasNext()) {
            Favorite element = iterator.next();
            if (element.getId().getCustomer_id().equals(customerId)) count++;
        }
        return count;
    }

    public List<printHalls> getFavoriteBanquetHalls(int page, int pageSize, Long customerId) {

        ArrayList<printHalls> printFavoriteHallsArrayList = new ArrayList<>();

        Iterable<Favorite> favorites = favoriteRepository.findAll();
        Iterator<Favorite> iterator = favorites.iterator();
        while (iterator.hasNext()) {
            Favorite element = iterator.next();

            if (element.getId().getCustomer_id().equals(customerId))
            {
                BanquetHalls banquetHalls = banquetHallsRepository.findById(element.getId().getBanquet_halls_id()).orElseThrow();
                printHalls e = new printHalls();
                e.setBanquet_halls_id(banquetHalls.getBanquet_halls_id());
                e.setCategory_id(banquetHalls.getCategory_id());
                e.setBanquet_halls_name(banquetHalls.getBanquet_halls_name());
                e.setDescript(banquetHalls.getDescript());
                e.setImage_path(banquetHalls.getImage_path());
                e.setAdress(banquetHalls.getAdress());
                e.setPrice(banquetHalls.getPrice());

                printFavoriteHallsArrayList.add(e);
            }
        }

        ArrayList<printHalls> printHallsArrayList = new ArrayList<>();

        int i = page * pageSize;

        System.out.println("page = " + page);
        System.out.println("pageSize = " + pageSize);
        System.out.println("i = " + i);
        System.out.println("page * pageSize + pageSize = " + (page * pageSize + pageSize));

        while ( i >= page * pageSize && i < page * pageSize + pageSize && i < printFavoriteHallsArrayList.size() ) {

            printHallsArrayList.add(printFavoriteHallsArrayList.get(i));
            i++;
        }

        return printHallsArrayList;

    }

    public List<Long> getAllFavoriteBanquetHallsId(Long customerId) {

        ArrayList<Long> printAllFavoriteHallsArrayList = new ArrayList<>();

        Iterable<Favorite> favorites = favoriteRepository.findAll();
        Iterator<Favorite> iterator = favorites.iterator();
        while (iterator.hasNext()) {
            Favorite element = iterator.next();

            if (element.getId().getCustomer_id().equals(customerId))
            {
                printAllFavoriteHallsArrayList.add(element.getId().getBanquet_halls_id());
            }
        }

        return printAllFavoriteHallsArrayList;

    }
}