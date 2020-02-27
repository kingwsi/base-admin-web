package com.kingwsi.bs.api;

import io.swagger.annotations.Api;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Description: <br>
 * Comments Name: DebugController<br>
 * Date: 2019/8/19 15:37<br>
 * Author: wangshu<br>
 */
@Api(tags = "debug")
@RestController
@RequestMapping("/debug")
public class DebugController {

    private final HttpServletRequest httpServletRequest;

    private final HttpServletResponse httpServletResponse;

    public DebugController(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    /**
     * 在响应式编程中，请求和响应不再是HttpServletRequest和HttpServletResponse，而是变成了ServerRequest和ServerResponse
     *
     * @return Mono和Flux主要用于响应式编程的“异步”数据流处理，不像我们以前直接返回String/List<T>....，而是直接包装成
     * Mono和Flux对象。见文知意，Mono主要用于返回单个数据，Flux用于返回多个数据。比如我们要根据id查询某个User，
     * 那返回的肯定就是一个User，那么需要包装成Mono<User>；若我们需要获取所有User，这是一个集合，我们需要包装成
     * Flux<User>。这里的单个数据并不是指一个数据，而是指封装好的一个对象；多个数据就是多个对象
     * ServerRequest request, ServerResponse response
     */
    @GetMapping("/times")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }

    @GetMapping("/req-test")
    public String test() {
        String rid = httpServletRequest.getHeader("rid");
        if (rid == null) {
            httpServletResponse.setHeader("rid", String.valueOf(System.nanoTime()));
        }
        return "success";
    }
}
