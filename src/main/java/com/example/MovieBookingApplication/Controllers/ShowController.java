package com.example.MovieBookingApplication.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MovieBookingApplication.Entities.Show;
import com.example.MovieBookingApplication.dtos.ShowDTO;
import com.example.MovieBookingApplication.services.ShowService;

@RestController
@RequestMapping("/api/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @GetMapping("/getAllShows")
    public ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/getShowsByTheater/{id}")
    public ResponseEntity<List<Show>> getShowsByTheater(@PathVariable Long id){
        return ResponseEntity.ok(showService.getShowsByTheaterId(id));
    }

    @GetMapping("/getShowsByMovie/{id}")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long id){
        return ResponseEntity.ok(showService.getShowsByMovieId(id));
    }

    @PostMapping("/addShow")
    public ResponseEntity<Show> addShow(@RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.addShow(showDTO));
    }

    @PutMapping("/updateShow/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable  Long id,@RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.updateShow(id, showDTO));
    }

    @DeleteMapping("/deleteShow/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id){
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }
}
