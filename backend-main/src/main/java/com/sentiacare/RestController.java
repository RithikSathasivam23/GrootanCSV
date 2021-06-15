package com.sentiacare;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

public class RestController {

public static void main(String[] args){
    Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(7001);
    app.post("/uploadcsv", context -> {
        context.result(Services.uploadcsv(context));
    });
}
}
