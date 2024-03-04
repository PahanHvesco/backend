package com.myapp.backend.controller;

import com.myapp.backend.model.History;
import org.springframework.web.bind.annotation.*;
import com.myapp.backend.service.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public List<History> getAllHistory() {
        return historyService.getAllHistory();
    }

    @GetMapping("/{id}")
    public History getHistoryById(@PathVariable Long id) {
        return historyService.getHistoryById(id);
    }

    @PostMapping
    public History createHistory(@RequestBody History history) {
        return historyService.createHistory(history);
    }

    @PutMapping("/{id}")
    public History updateHistory(@PathVariable long id, @RequestBody History history) {
        return historyService.updateHistory(id, history);
    }
    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
    }
}
