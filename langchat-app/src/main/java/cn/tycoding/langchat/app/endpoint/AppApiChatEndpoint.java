/*
 * Copyright (c) 2024 LangChat. TyCoding All Rights Reserved.
 *
 * Licensed under the GNU Affero General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.tycoding.langchat.app.endpoint;

import cn.hutool.core.lang.Dict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

/**
 * @author tycoding
 * @since 2024/7/26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/langchat/openapi/v1")
public class AppApiChatEndpoint {

    @PostMapping("/test")
    public Object test(@RequestBody Object obj) {
        log.info("x: {}", obj);
        return Dict.create().set("message", "你好呀").set("threadId", "111");
    }

    @PostMapping("/test2")
    public Object test2(@RequestBody Object obj) throws InterruptedException, IOException {
        log.info("Received: {}", obj);
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    emitter.send("Data: " + i + "\n", MediaType.TEXT_PLAIN);
                    Thread.sleep(1000);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }
}