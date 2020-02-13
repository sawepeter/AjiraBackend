package com.devsawe.demo.controllers;

import com.devsawe.demo.ResourceNotFoundException;
import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.TodoModel;
import com.devsawe.demo.repositories.TodoRepository;
import com.devsawe.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    //works creates todo list for the currently logged in user.
    @PostMapping("/users/todos")
    public TodoModel createTodoList(@Valid @RequestBody TodoModel todoModel) {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        todoModel.setUserId(customUserDetails.getId());
        return todoRepository.save(todoModel);
    }

    @PutMapping("my-todos/{id}")
   public ResponseEntity<?> update(@PathVariable long id, @RequestBody TodoModel todoModel){
        Map<String, String> resp = new HashMap<>();
     CustomUserDetails customUserDetails = (CustomUserDetails)
        SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
     TodoModel todoModel1 = todoRepository.findById(id).orElse(null);

     if (todoModel1 == null){
         resp.put("state", "danger");
         resp.put("msg", "id not found");
         return ResponseEntity.ok(resp);
     }
     /*todoModel1.setId(todoModel.getId());*/
     todoModel1.setTodotitle(todoModel.getTodotitle());
     todoModel1.setTodotime(todoModel.getTodotime());
    // todoModel1.setUserId(customUserDetails.getId());
     todoRepository.save(todoModel1);
     resp.put("state", "success");
     resp.put("msg", "Todo updated successfully");
     return  ResponseEntity.ok(resp);
    }

    @PutMapping("my-todos/completed/{id}")
    public ResponseEntity<?> completed(@PathVariable long id){
        Map<String, String> resp = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        TodoModel todoModel1 = todoRepository.findById(id).orElse(null);

        if (todoModel1 == null){
            resp.put("state", "danger");
            resp.put("msg","The user does not a todo with that id");
            return ResponseEntity.ok(resp);
        }

        todoModel1.setStatus("Completed");
        //todoModel1.setUserId(customUserDetails.getId());
        todoRepository.save(todoModel1);
        resp.put("state", "success");
        resp.put("msg", "state updated successfully");
        return ResponseEntity.ok(resp);
    }

    //get the todo list of the current user(logged in)
    @GetMapping("/my-todos")
    public ResponseEntity<List<TodoModel>> currentUserToDos() {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        List<TodoModel> todoModels = todoRepository.findByUserId(customUserDetails.getId());
        return ResponseEntity.ok(todoModels);
    }

    //get the todotitle by a specific date
    @GetMapping("/today-todos")
    public ResponseEntity<List<TodoModel>> todayToDos(@RequestParam(value = "date") String date ){
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
        List<TodoModel> todoModels = todoRepository.findByCreatedAt(customUserDetails.getId(),date);
        return ResponseEntity.ok(todoModels);
    }

    @DeleteMapping("/my-todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        CustomUserDetails customUserDetails = (CustomUserDetails)
            SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        TodoModel todoModel = todoRepository.findById(id).orElse(null);

        if (todoModel == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        todoRepository.delete(todoModel);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

 /*   @GetMapping("/mytodos-today")
    public ResponseEntity<List<Map<String, String>>> Todostoday(@RequestParam(value = "date") String date){
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        List<Map<String, String>> todotitle;
         todotitle = todoRepository.findByCreatedAt(customUserDetails.getId(),date);
        return  ResponseEntity.ok(todotitle);
    }*/

    /*@GetMapping("/mytodos-week")
    public ResponseEntity<List<Map<String, String>>> TodosWeek(){
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        List<Map<String, String>> todotitle;
        todotitle = todoRepository.findByWeek(customUserDetails.getId());
        return ResponseEntity.ok(todotitle);
    }*/
}
