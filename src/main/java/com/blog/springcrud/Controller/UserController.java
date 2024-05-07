package com.blog.springcrud.Controller;

import com.blog.springcrud.exception.ResouceNotFoundException;
import com.blog.springcrud.Repository.UserRepository;
import com.blog.springcrud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
//    TODO:  get all users data and store to the databaase
    @GetMapping
    public List<User> getAll(){
        return this.userRepository.findAll();
    }

//    TODO: get all users  by the id that will use for the primary keys
    @GetMapping("/{id}")
    public User getById(@PathVariable(value = "id")long userId){
        return this.userRepository.findById(userId).orElseThrow(()-> new ResouceNotFoundException("User not found with id: "+userId));

    }

//    TODO: Create User
    @PostMapping("/add")
    public User add(@RequestBody User user){
        return this.userRepository.save(user);
    }
//    TODO: Update user
    @PutMapping("/update/{id}")
    public User update(@RequestBody User user, @PathVariable("id")long userId){
        User existingUser =this.userRepository.findById(userId).orElseThrow(()->new ResouceNotFoundException("User not Found with id: "+userId));
        existingUser.setName(user.getName());
        existingUser.setCity(user.getCity());
        existingUser.setEmail(user.getEmail());
        return this.userRepository.save(existingUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable("id")long userId){
        User existingUser =this.userRepository.findById(userId).orElseThrow(()->new ResouceNotFoundException("User not found with id:"+userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
