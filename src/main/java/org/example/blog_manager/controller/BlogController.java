package org.example.blog_manager.controller;

import org.example.blog_manager.model.Blog;
import org.example.blog_manager.service.BlogService;
import org.example.blog_manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/blog")
public class BlogController {
    // Constants
    private final String EL_NAME = "blog";
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
    BlogService blogService;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ModelAndView getListPage(){
        ModelAndView modelAndView = new ModelAndView(LIST_PAGE);
        modelAndView.addObject("eList", blogService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getCreatePage(){
        ModelAndView modelAndView = new ModelAndView(CREATE_PAGE);
        modelAndView.addObject("el", new Blog());
        modelAndView.addObject("cateList", categoryService.findAll());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView getCreated(@ModelAttribute Blog el){
        blogService.save(el);
        ModelAndView modelAndView = new ModelAndView(LIST_PAGE);
        modelAndView.addObject("eList", blogService.findAll());
        modelAndView.addObject("msg", CREATE_MSG);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditPage(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView(EDIT_PAGE);
        modelAndView.addObject("el", blogService.findById(id));
        modelAndView.addObject("cateList", categoryService.findAll());
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView getEdited(@ModelAttribute Blog el){
        blogService.save(el);
        ModelAndView modelAndView = new ModelAndView(LIST_PAGE);
        modelAndView.addObject("eList", blogService.findAll());
        modelAndView.addObject("msg", EDIT_MSG);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView getViewPage(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView(VIEW_PAGE);
        modelAndView.addObject("el", blogService.findById(id));
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeletePage(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView(DELETE_PAGE);
        modelAndView.addObject("el", blogService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView getDeleted(@ModelAttribute Blog el){
        blogService.remove(el.getId());
        ModelAndView modelAndView = new ModelAndView(LIST_PAGE);
        modelAndView.addObject("eList", blogService.findAll());
        modelAndView.addObject("msg", DELETE_MSG);
        return modelAndView;
    }

}
