package com.voca.eco.common.config;

import com.voca.eco.app.chat.ChatHandler;
import com.voca.eco.common.util.CmmUtil;
import com.voca.eco.common.util.EncryptUtil;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attoparser.IAttributeSequenceHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.thymeleaf.model.IAttribute;

@Slf4j
@EnableWebSocket
@RequiredArgsConstructor
@Configuration
public class WebSoketConfig implements WebSocketConfigurer {
    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("WebSocket Execute!");

        registry.addHandler(chatHandler, "/ws/*/*")
                /*
                    실제 WebSocket 연결 및 메시지 처리를 담당하는 핸들러 > 클라이언트에서 WebSocket에 접근할 수 있는 엔드포인트
                    여기로 html에서 요청을 보내야 websocket이 실행되면서 채팅을 할 수 있음
                 */
                .setAllowedOrigins("*") // 클라이언트 요청을 수락하는 원천을 설정 > url과 일치해야 연결이 허용
                .addInterceptors( // Websocket 연결 시, 핸드셰이크 과정에서 추가적인 작업을 수행하는 인터셉터 등록
                        // HttpSessionHandshakeInterceptor를 구현한 익명 클래스를 생성하여 인터셉터 정의
                        new HttpSessionHandshakeInterceptor() { // Websocket 연결 시, 핸드셰이크가 진행되기 전에 실행됨
                            @Override
                            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                    WebSocketHandler wsHandler, Map<String, Object> attributes)
                                    throws Exception {
                                // beforeHandshake > 핸드셰이크 과정 직전에 실행 / 요청 정보를 처리하고 속성을 설정
                                /* 클라이언트의 요청 url경로에서 roomId와 customerId, traderId를 추출 */
                                String path = CmmUtil.nvl(request.getURI().getPath());
                                // 클라이언트의 요청 url경로를 변수 path에 저장함
                                log.info("path : " + path);

                                String[] urlInfo = path.split("/"); // url경로를 '/'기준으로 나누어 배열에 저장

                                String roomName = CmmUtil.nvl(urlInfo[2]); // 배열에서 roomName 추출
                                String userName = CmmUtil.nvl(urlInfo[3]); // 배열에서 customerId 추출
                                String roomNameHash = EncryptUtil.encHashSHA256(roomName);
                                // 방이름을 해싱하여 보안을 강화하는 작업 수행

                                log.info("roomName : " + roomName + " / userName : " + userName);
                                log.info("roomNameHash : " + roomNameHash);

                                attributes.put("roomName", roomName);
                                attributes.put("userName", userName);
                                attributes.put("roomNameHash", roomNameHash);
                                // 추출한 정보를 Websocket 연결 시에 속성으로 전달하기 위해 Map에 저장

                                return super.beforeHandshake(request, response, wsHandler, attributes); // 핸드셰이크 진행
                            }
                        }
                );
    }
}