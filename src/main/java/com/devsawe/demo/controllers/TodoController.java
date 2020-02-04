package com.devsawe.demo.controllers;

import com.devsawe.demo.ResourceNotFoundException;
import com.devsawe.demo.authentication.CustomUserDetails;
import com.devsawe.demo.entities.TodoModel;
import com.devsawe.demo.repositories.TodoRepository;
import com.devsawe.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    //doesnot work but works with the my-todos path.
   /* @GetMapping("/users/{userId}/todolist")
    public Page<TodoModel> getAllTodoListByUserId(@PathVariable(value = "UserId") Long userId,
                                                  Pageable pageable) {
        return todoRepository.findByUserId(userId, pageable);
    }*/

    //works creates todo list for the currently logged in user.
    @PostMapping("/users/todos")
    public TodoModel createTodoList(@Valid @RequestBody TodoModel todoModel) {
        CustomUserDetails customUserDetails = (CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        todoModel.setUserId(customUserDetails.getId());
        return todoRepository.save(todoModel);
    }

    @PutMapping("users/{userId}/todolist/{todoId}")
    public TodoModel updateTodoList(@PathVariable(value = "userId") Long userId,
                                    @PathVariable(value = "todoId") Long todoId,
                                    @Valid @RequestBody TodoModel todoRequest) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("userId" + userId + "not found");
        }

        return todoRepository.findById(todoId).map(todoModel -> {
            todoModel.setTodotitle(todoRequest.getTodotitle());
            return todoRepository.save(todoModel);
        }).orElseThrow(() -> new ResourceNotFoundException("todoId" + todoId + "not found"));
    }

    @DeleteMapping("/users/{userId}/todolist/{todolist}")
    public ResponseEntity<?> deleteTodoList(@PathVariable(value = "userId") Long userId,
                                            @PathVariable(value = "todoId") Long todoId) {
        return todoRepository.findByIdAndUserId(todoId, userId).map(todoModel -> {
            todoRepository.delete(todoModel);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Todo list not found with id " + todoId + " and userId " + userId));
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
}
