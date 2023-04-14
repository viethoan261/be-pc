package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Comment;
import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.Order;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.payload.statistics.CommentsDTO;
import com.example.adambackend.payload.statistics.ExceptDTO;
import com.example.adambackend.payload.statistics.OrdersDTO;
import com.example.adambackend.payload.statistics.StackDTO;
import com.example.adambackend.payload.statistics.StackedDTO;
import com.example.adambackend.payload.statistics.StatisticsDTO;
import com.example.adambackend.payload.statistics.StatusDTO;
import com.example.adambackend.repository.AccountRepository;
import com.example.adambackend.repository.CategoryRepository;
import com.example.adambackend.repository.ColorRepository;
import com.example.adambackend.repository.CommentRepository;
import com.example.adambackend.repository.MaterialRepository;
import com.example.adambackend.repository.OrderRepository;
import com.example.adambackend.repository.ProductRepository;
import com.example.adambackend.repository.TagRepository;
import com.example.adambackend.service.CategoryService;
import com.example.adambackend.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public StatisticsDTO getStatistics() {
        StatisticsDTO dto = new StatisticsDTO();
        //acountt
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        ExceptDTO except = new ExceptDTO();
        List<Account> accounts = accountRepository.findAll();
        except.setTotal(accounts.size());
        except.setTotalExceptToday(accounts.stream().filter(t -> t.getCreateDate() != null && t.getCreateDate().isBefore(yesterday)).collect(Collectors.toList()).size());
        dto.setAccounts(except);

        except = new ExceptDTO();
        List<Product> products = productRepository.findAll();
        except.setTotal(products.size());
        except.setTotalExceptToday(products.stream().filter(t -> t.getCreateDate() != null && t.getCreateDate().isBefore(yesterday)).collect(Collectors.toList()).size());

        dto.setProducts(except);

        //orders
        OrdersDTO order = new OrdersDTO();
        List<Order> orders = orderRepository.findAll();
        List<StatusDTO> status = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus,
                        Collectors.counting()))
                .entrySet().stream()
                .map(e -> new StatusDTO(e.getKey().toString(), e.getValue()))
                .collect(Collectors.toList());
        for (StatusDTO s: status
           ) {
            s.setLabel(this.setLabel(Integer.parseInt(s.getLabel())));
        }
        order.setStatus(status);
        order.setTotal(orders.size());
        dto.setOrders(order);

        //stacked
        StackedDTO stacked = new StackedDTO();
        StackDTO colors = new StackDTO();
        StackDTO materials = new StackDTO();
        StackDTO categories = new StackDTO();
        StackDTO tags = new StackDTO();
        List<Color> c = colorRepository.getAll();
        colors.setLabel("Màu");
        colors.setTotal(c.size());
        colors.setIsActive(c.stream().filter(t -> t.getIsActive() == true).collect(Collectors.toList()).size());
        stacked.setColors(colors);

        List<Material> m = materialRepository.getAll();
        materials.setLabel("Nguyên liệu");
        materials.setTotal(m.size());
        materials.setIsActive(m.stream().filter(t -> t.getIsActive() == true).collect(Collectors.toList()).size());
        stacked.setMaterials(materials);

        List<Category> ca = categoryRepository.getAll();
        categories.setLabel("Phân loại");
        categories.setTotal(ca.size());
        categories.setIsActive(ca.stream().filter(t -> t.getIsActive() == true).collect(Collectors.toList()).size());
        stacked.setCategories(categories);

        List<Tag> t = tagRepository.getAll();
        tags.setLabel("Tags");
        tags.setTotal(c.size());
        tags.setIsActive(t.stream().filter(i -> i.getIsActive() == true).collect(Collectors.toList()).size());
        stacked.setTags(tags);
        dto.setStacked(stacked);

        List<Integer> productIDS = products.stream().map(i -> i.getId()).collect(Collectors.toList());
        List<Comment> comments = commentRepository.getAll(productIDS);
        CommentsDTO comment = new CommentsDTO();
        comment.setTotal(comments.size());
        comment.setFiveStar(comments.stream().filter(i -> i.getVote() == 5).collect(Collectors.toList()).size());

        dto.setComments(comment);
        return dto;
    }

    private String setLabel(int x) {
        String res = "";
        switch (x) {
            case -1:
                res = "Hoàn trả";
                break;
            case 0:
                res = "Hủy bỏ";
                break;
            case 1:
                res = "Đang chờ";
                break;
            case 2:
                res = "Hoạt động";
                break;
            case 3:
                res = "Đã nhận";
                break;
            case 4:
                res = "Trì hoãn";
                break;
            case 5:
                res = "Kiểm tra";
                break;
            case 6:
                res = "Thành công";
                break;
            case 7:
                res ="Đang giao";
                break;
            default :
                res = "Không";
        }
        return res;
    }
}
