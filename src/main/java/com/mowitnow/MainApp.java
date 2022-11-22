package com.mowitnow;

import com.mowitnow.exception.FileFormatInvalidException;
import com.mowitnow.model.Position;
import com.mowitnow.service.TondeuseService;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class MainApp {

    public static void main(final String[] args) throws FileNotFoundException {
        if(args == null || args.length == 0) {
            throw new FileFormatInvalidException("Please provide the input movement file");
        }
        TondeuseService tondeuseService = new TondeuseService(Path.of(args[0]));
        List<Position> p = tondeuseService.processMovement();
        p.forEach(position -> log.info(position.toString()));
    }
}
