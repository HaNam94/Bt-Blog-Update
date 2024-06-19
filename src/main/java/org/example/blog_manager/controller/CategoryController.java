package org.example.blog_manager.controller;

import org.example.blog_manager.model.Category;
import org.example.blog_manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/category")
public class CategoryController {
    // Constants
    private final String EL_NAME = "category";
    private final String LIST_PAGE = EL_NAME + "/list";
    private final String CREATE_PAGE = EL_NAME + "/create";
    private final String EDIT_PAGE = EL_NAME + "/edit";
    private final String DELETE_PAGE = EL_NAME + "/delete";
    private final String VIEW_PAGE = EL_NAME + "/view";
    private final String REDIRECT_TO_LIST = "redirect:/" + EL_NAME;

    private final String LIST_MSG = "Total of " + EL_NAME + ": ";
    private final String CREATE_MSG = "Create new " + EL_NAME + " successfully.";
    private final String EDIT_MSG = "Update the " + EL_NAME + " successfully.";
    private final String DELETE_MSG = "Delete the " + EL_NAME + " successfully.";
    private final String DELETE_MANY_MSG = "Delete the selected " + EL_NAME + "(s) successfully.";

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ModelAndView getListPage(){
        ModelAndView modelAndView = new ModelAndView(LIST_PAGE);
        modelAndView.addObject("eList", categoryService.findAll());
        modelAndView.addObject("COUNT_MSG", LIST_MSG + categoryService.count());
        modelAndView.addObject("ids", new int[]{}); // only for delete many
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getCreatePage(){
        ModelAndView modelAndView = new ModelAndView(CREATE_PAGE);
        modelAndView.addObject("el", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView getCreated(@ModelAttribute Category el, RedirectAttributes redirect){
        ModelAndView modelAndView = new ModelAndView(REDIRECT_TO_LIST);
        categoryService.save(el);
        redirect.addFlashAttribute("msg", CREATE_MSG);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditPage(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView(EDIT_PAGE);
        modelAndView.addObject("el", categoryService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView getEditPage(@ModelAttribute Category el, RedirectAttributes redirect){
        ModelAndView modelAndView = new ModelAndView(REDIRECT_TO_LIST);
        categoryService.save(el);
        redirect.addFlashAttribute("msg", EDIT_MSG);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeletePage(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView(DELETE_PAGE);
        modelAndView.addObject("el", categoryService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView getDeleted(@ModelAttribute Category el, RedirectAttributes redirect){
        ModelAndView modelAndView = new ModelAndView(REDIRECT_TO_LIST);
        categoryService.delete(el.getCategoryId());
        redirect.addFlashAttribute("msg", DELETE_MSG);
        return modelAndView;
    }

    // Delete many elements
    @PostMapping("/delete-many")
    public ModelAndView getDeleteManyDone(@RequestParam int[] ids, RedirectAttributes redirect){
        ModelAndView modelAndView = new ModelAndView(REDIRECT_TO_LIST);
        if(ids != null) {
            for (Integer id : ids) {
                categoryService.delete(id);
            }
        }
        redirect.addFlashAttribute("msg", DELETE_MANY_MSG);
        return modelAndView;
    }

    // Get product list
    @GetMapping("blog-list/{id}")
    public ModelAndView getProductListPage(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("/category/blog_detail");
        modelAndView.addObject("category", categoryService.findById(id));
        modelAndView.addObject("size", categoryService.findById(id).getBlogList().size());
        return modelAndView;
    }
}
