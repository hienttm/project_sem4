/*
 Navicat Premium Dump SQL

 Source Server         : docker_mysql
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : ProjectSem4

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 05/07/2024 14:33:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answers
-- ----------------------------
DROP TABLE IF EXISTS `answers`;
CREATE TABLE `answers`  (
                            `answer_id` int NOT NULL AUTO_INCREMENT,
                            `answer_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `tof` bit(1) NOT NULL,
                            `question_id` int NOT NULL,
                            PRIMARY KEY (`answer_id`) USING BTREE,
                            INDEX `FK3erw1a3t0r78st8ty27x6v3g1`(`question_id` ASC) USING BTREE,
                            CONSTRAINT `FK3erw1a3t0r78st8ty27x6v3g1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cart_courses
-- ----------------------------
DROP TABLE IF EXISTS `cart_courses`;
CREATE TABLE `cart_courses`  (
                                 `cart_course_id` int NOT NULL AUTO_INCREMENT,
                                 `update_at` datetime(6) NOT NULL,
                                 `course_id` int NOT NULL,
                                 `user_id` int NOT NULL,
                                 PRIMARY KEY (`cart_course_id`) USING BTREE,
                                 INDEX `FKsqi9btt9o3wa75exnrxgergyf`(`course_id` ASC) USING BTREE,
                                 INDEX `FKn65c63tsdowiiypjlh0pmp4eh`(`user_id` ASC) USING BTREE,
                                 CONSTRAINT `FKn65c63tsdowiiypjlh0pmp4eh` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT `FKsqi9btt9o3wa75exnrxgergyf` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
                               `category_id` int NOT NULL AUTO_INCREMENT,
                               `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               `update_at` datetime(6) NOT NULL,
                               `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                               `status` int NOT NULL,
                               PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category_sales
-- ----------------------------
DROP TABLE IF EXISTS `category_sales`;
CREATE TABLE `category_sales`  (
                                   `category_sale_id` int NOT NULL AUTO_INCREMENT,
                                   `category_sale_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   PRIMARY KEY (`category_sale_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chapters
-- ----------------------------
DROP TABLE IF EXISTS `chapters`;
CREATE TABLE `chapters`  (
                             `chapter_id` int NOT NULL AUTO_INCREMENT,
                             `chapter_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `chapter_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `chapter_video` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `create_at` datetime(6) NOT NULL,
                             `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                             `status` int NOT NULL,
                             `course_id` int NOT NULL,
                             PRIMARY KEY (`chapter_id`) USING BTREE,
                             INDEX `FK6h1m0nrtdwj37570c0sp2tdcs`(`course_id` ASC) USING BTREE,
                             CONSTRAINT `FK6h1m0nrtdwj37570c0sp2tdcs` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_types
-- ----------------------------
DROP TABLE IF EXISTS `course_types`;
CREATE TABLE `course_types`  (
                                 `course_type_id` int NOT NULL AUTO_INCREMENT,
                                 `create_at` datetime(6) NOT NULL,
                                 `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                 `status` int NOT NULL,
                                 `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                 PRIMARY KEY (`course_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
                            `course_id` int NOT NULL AUTO_INCREMENT,
                            `course_file` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `course_video` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `free_numbers` int NOT NULL,
                            `price` double NOT NULL,
                            `status` int NOT NULL,
                            `category_id` int NOT NULL,
                            `censor_id` int NULL DEFAULT NULL,
                            `course_type_id` int NOT NULL,
                            `user_id` int NOT NULL,
                            `image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                            `sale_price` double NULL DEFAULT NULL,
                            PRIMARY KEY (`course_id`) USING BTREE,
                            INDEX `FK72l5dj585nq7i6xxv1vj51lyn`(`category_id` ASC) USING BTREE,
                            INDEX `FKr18uqmsrooldk4kmvcs0wrawf`(`censor_id` ASC) USING BTREE,
                            INDEX `FK4f3etk0ltox5d1s1fwcrvqw9f`(`course_type_id` ASC) USING BTREE,
                            INDEX `FK51k53m6m5gi9n91fnlxkxgpmv`(`user_id` ASC) USING BTREE,
                            CONSTRAINT `FK4f3etk0ltox5d1s1fwcrvqw9f` FOREIGN KEY (`course_type_id`) REFERENCES `course_types` (`course_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `FK51k53m6m5gi9n91fnlxkxgpmv` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `FK72l5dj585nq7i6xxv1vj51lyn` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `FKr18uqmsrooldk4kmvcs0wrawf` FOREIGN KEY (`censor_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `courses_chk_1` CHECK (`price` >= 1)
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events`  (
                           `event_id` int NOT NULL AUTO_INCREMENT,
                           `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `end_at` datetime(6) NOT NULL,
                           `event_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `max_sale` double NOT NULL,
                           `min_price` double NOT NULL,
                           `quantity` int NOT NULL,
                           `sale` double NOT NULL,
                           `start_at` datetime(6) NOT NULL,
                           `status` int NOT NULL,
                           `category_id` int NULL DEFAULT NULL,
                           `area` int NULL DEFAULT NULL,
                           PRIMARY KEY (`event_id`) USING BTREE,
                           INDEX `FK84rnm8laswcdog4la2a9iabtd`(`category_id` ASC) USING BTREE,
                           CONSTRAINT `FK84rnm8laswcdog4la2a9iabtd` FOREIGN KEY (`category_id`) REFERENCES `category_sales` (`category_sale_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exams
-- ----------------------------
DROP TABLE IF EXISTS `exams`;
CREATE TABLE `exams`  (
                          `exam_id` int NOT NULL AUTO_INCREMENT,
                          `create_at` datetime(6) NOT NULL,
                          `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                          `exam_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `number_question` int NOT NULL,
                          `ratio_pass` int NULL DEFAULT NULL,
                          `status` int NOT NULL,
                          `course_id` int NOT NULL,
                          `time` int NOT NULL,
                          PRIMARY KEY (`exam_id`) USING BTREE,
                          INDEX `FKr1qm93flajdaclug2fg8i7bcg`(`course_id` ASC) USING BTREE,
                          CONSTRAINT `FKr1qm93flajdaclug2fg8i7bcg` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_details
-- ----------------------------
DROP TABLE IF EXISTS `order_details`;
CREATE TABLE `order_details`  (
                                  `order_detail_id` int NOT NULL AUTO_INCREMENT,
                                  `price` double NOT NULL,
                                  `course_id` int NOT NULL,
                                  `order_id` int NOT NULL,
                                  PRIMARY KEY (`order_detail_id`) USING BTREE,
                                  INDEX `FKtc2uxybe6r9ak6sd66whjd27`(`course_id` ASC) USING BTREE,
                                  INDEX `FKjyu2qbqt8gnvno9oe9j2s2ldk`(`order_id` ASC) USING BTREE,
                                  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                  CONSTRAINT `FKtc2uxybe6r9ak6sd66whjd27` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
                           `order_id` int NOT NULL AUTO_INCREMENT,
                           `create_at` datetime(6) NOT NULL,
                           `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                           `status` int NOT NULL,
                           `update_at` datetime(6) NOT NULL,
                           `event_id` int NULL DEFAULT NULL,
                           `payment_method_id` int NOT NULL,
                           `user_id` int NOT NULL,
                           `payment_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                           `total` double NULL DEFAULT NULL,
                           PRIMARY KEY (`order_id`) USING BTREE,
                           INDEX `FK43g2yroy6l7lfomw37wajkqrn`(`event_id` ASC) USING BTREE,
                           INDEX `FKa03ljb6t6oa6mqtoifuwkb0kw`(`payment_method_id` ASC) USING BTREE,
                           INDEX `FK32ql8ubntj5uh44ph9659tiih`(`user_id` ASC) USING BTREE,
                           CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT `FK43g2yroy6l7lfomw37wajkqrn` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT `FKa03ljb6t6oa6mqtoifuwkb0kw` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`payment_method_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for payment_methods
-- ----------------------------
DROP TABLE IF EXISTS `payment_methods`;
CREATE TABLE `payment_methods`  (
                                    `payment_method_id` int NOT NULL AUTO_INCREMENT,
                                    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                    `payment_method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                    PRIMARY KEY (`payment_method_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for policies
-- ----------------------------
DROP TABLE IF EXISTS `policies`;
CREATE TABLE `policies`  (
                             `policy_id` int NOT NULL AUTO_INCREMENT,
                             `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                             `ratio` double NOT NULL,
                             `status` int NOT NULL,
                             `update_at` datetime(6) NOT NULL,
                             `category_id` int NOT NULL,
                             PRIMARY KEY (`policy_id`) USING BTREE,
                             INDEX `FKcq4b7t64xitar97p9rkxx66iq`(`category_id` ASC) USING BTREE,
                             CONSTRAINT `FKcq4b7t64xitar97p9rkxx66iq` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for questions
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions`  (
                              `question_id` int NOT NULL AUTO_INCREMENT,
                              `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              `exam_id` int NOT NULL,
                              `status` int NOT NULL,
                              PRIMARY KEY (`question_id`) USING BTREE,
                              INDEX `FKrk78bmt53fns7np8casqa3q44`(`exam_id` ASC) USING BTREE,
                              CONSTRAINT `FKrk78bmt53fns7np8casqa3q44` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`exam_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for request_types
-- ----------------------------
DROP TABLE IF EXISTS `request_types`;
CREATE TABLE `request_types`  (
                                  `request_type_id` int NOT NULL AUTO_INCREMENT,
                                  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                  `request_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `status` int NOT NULL,
                                  PRIMARY KEY (`request_type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for requests
-- ----------------------------
DROP TABLE IF EXISTS `requests`;
CREATE TABLE `requests`  (
                             `request_id` int NOT NULL AUTO_INCREMENT,
                             `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                             `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `create_at` datetime(6) NOT NULL,
                             `status` int NOT NULL,
                             `update_at` datetime(6) NOT NULL,
                             `request_type_id` int NOT NULL,
                             `user_id` int NOT NULL,
                             PRIMARY KEY (`request_id`) USING BTREE,
                             INDEX `FKsnu8pyufweklimrfnyf6vrdcr`(`request_type_id` ASC) USING BTREE,
                             INDEX `FK8usbpx9csc6opbjg1d7kvtf8c`(`user_id` ASC) USING BTREE,
                             CONSTRAINT `FK8usbpx9csc6opbjg1d7kvtf8c` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                             CONSTRAINT `FKsnu8pyufweklimrfnyf6vrdcr` FOREIGN KEY (`request_type_id`) REFERENCES `request_types` (`request_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for reviews
-- ----------------------------
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews`  (
                            `review_id` int NOT NULL AUTO_INCREMENT,
                            `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                            `create_at` datetime(6) NOT NULL,
                            `star_number` int NOT NULL,
                            `status` int NOT NULL,
                            `update_at` datetime(6) NOT NULL,
                            `course_id` int NOT NULL,
                            `user_id` int NOT NULL,
                            `featured` int NULL DEFAULT NULL,
                            PRIMARY KEY (`review_id`) USING BTREE,
                            INDEX `FKccbfc9u1qimejr5ll7yuxbtqs`(`course_id` ASC) USING BTREE,
                            INDEX `FKcgy7qjc1r99dp117y9en6lxye`(`user_id` ASC) USING BTREE,
                            CONSTRAINT `FKccbfc9u1qimejr5ll7yuxbtqs` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
                          `role_id` int NOT NULL AUTO_INCREMENT,
                          `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `status` int NOT NULL,
                          `update_at` datetime(6) NULL DEFAULT NULL,
                          PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for status_courses
-- ----------------------------
DROP TABLE IF EXISTS `status_courses`;
CREATE TABLE `status_courses`  (
                                   `status_id` int NOT NULL AUTO_INCREMENT,
                                   `status_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `update_at` datetime(6) NOT NULL,
                                   `course_id` int NULL DEFAULT NULL,
                                   `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                   `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                   PRIMARY KEY (`status_id`) USING BTREE,
                                   INDEX `FKsopgpcjgbbn1g7rmgx3xdomjf`(`course_id` ASC) USING BTREE,
                                   CONSTRAINT `FKsopgpcjgbbn1g7rmgx3xdomjf` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher_profits
-- ----------------------------
DROP TABLE IF EXISTS `teacher_profits`;
CREATE TABLE `teacher_profits`  (
                                    `teacher_profit_id` int NOT NULL AUTO_INCREMENT,
                                    `profit` double NOT NULL,
                                    `status` int NOT NULL,
                                    `order_id` int NOT NULL,
                                    `user_id` int NOT NULL,
                                    PRIMARY KEY (`teacher_profit_id`) USING BTREE,
                                    INDEX `FKly5sh63k9pjwtrb7qtb493l0x`(`order_id` ASC) USING BTREE,
                                    INDEX `FKgns29gjliugnt87wj3x0lntg0`(`user_id` ASC) USING BTREE,
                                    CONSTRAINT `FKgns29gjliugnt87wj3x0lntg0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `FKly5sh63k9pjwtrb7qtb493l0x` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher_registers
-- ----------------------------
DROP TABLE IF EXISTS `teacher_registers`;
CREATE TABLE `teacher_registers`  (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `bank_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                      `bank_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                      `create_at` datetime(6) NULL DEFAULT NULL,
                                      `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                                      `status` int NULL DEFAULT NULL,
                                      `update_at` datetime(6) NULL DEFAULT NULL,
                                      `user_id` int NULL DEFAULT NULL,
                                      `git` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `FKcc9g8fr946ijxqvh1e0u7bu9i`(`user_id` ASC) USING BTREE,
                                      CONSTRAINT `FKcc9g8fr946ijxqvh1e0u7bu9i` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tokens
-- ----------------------------
DROP TABLE IF EXISTS `tokens`;
CREATE TABLE `tokens`  (
                           `token_id` int NOT NULL AUTO_INCREMENT,
                           `create_at` datetime(6) NULL DEFAULT NULL,
                           `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                           `user_id` int NOT NULL,
                           PRIMARY KEY (`token_id`) USING BTREE,
                           INDEX `FK2dylsfo39lgjyqml2tbe0b0ss`(`user_id` ASC) USING BTREE,
                           CONSTRAINT `FK2dylsfo39lgjyqml2tbe0b0ss` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_answers
-- ----------------------------
DROP TABLE IF EXISTS `user_answers`;
CREATE TABLE `user_answers`  (
                                 `user_answer_id` int NOT NULL AUTO_INCREMENT,
                                 `answer_id` int NULL DEFAULT NULL,
                                 `user_id` int NOT NULL,
                                 `exam_id` int NULL DEFAULT NULL,
                                 PRIMARY KEY (`user_answer_id`) USING BTREE,
                                 INDEX `FKq9ubv2ar56hkwxokdbp72b5by`(`answer_id` ASC) USING BTREE,
                                 INDEX `FKk4u357ronsopa0vqf16deuxbt`(`user_id` ASC) USING BTREE,
                                 INDEX `FK9efuavaa7y69wswnxjubv0ss3`(`exam_id` ASC) USING BTREE,
                                 CONSTRAINT `FK9efuavaa7y69wswnxjubv0ss3` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`exam_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT `FKk4u357ronsopa0vqf16deuxbt` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT `FKq9ubv2ar56hkwxokdbp72b5by` FOREIGN KEY (`answer_id`) REFERENCES `answers` (`answer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 166 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_payments
-- ----------------------------
DROP TABLE IF EXISTS `user_payments`;
CREATE TABLE `user_payments`  (
                                  `user_payment_id` int NOT NULL AUTO_INCREMENT,
                                  `account_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `payment_method_id` int NOT NULL,
                                  `user_id` int NOT NULL,
                                  PRIMARY KEY (`user_payment_id`) USING BTREE,
                                  INDEX `FKfwvdisk0onklmwhreoml1joqh`(`payment_method_id` ASC) USING BTREE,
                                  INDEX `FK48m9vjig8w6q9fglp2w7urppv`(`user_id` ASC) USING BTREE,
                                  CONSTRAINT `FK48m9vjig8w6q9fglp2w7urppv` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                  CONSTRAINT `FKfwvdisk0onklmwhreoml1joqh` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`payment_method_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `role_id` int NULL DEFAULT NULL,
                               `user_id` int NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `FKh8ciramu9cc9q3qcqiv4ue8a6`(`role_id` ASC) USING BTREE,
                               INDEX `FKhfh9dx7w3ubf1co1vdev94g3f`(`user_id` ASC) USING BTREE,
                               CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                               CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
                          `user_id` int NOT NULL AUTO_INCREMENT,
                          `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `birthday` datetime(6) NOT NULL,
                          `create_at` datetime(6) NOT NULL,
                          `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `fullname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `gender` int NOT NULL,
                          `image` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `status` int NOT NULL,
                          `update_at` datetime(6) NOT NULL,
                          `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Procedure structure for GetAllCourseProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetAllCourseProcedure`;
delimiter ;;
CREATE PROCEDURE `GetAllCourseProcedure`(IN searchName VARCHAR(255))
BEGIN
SELECT c.course_id, c.course_file, c.course_name, c.course_video, c.free_numbers, c.image, c.price, c.sale_price, c.status, u1.fullname as username, censor.username as censor_name, cate.category_name, ct.type_name  FROM courses AS c
                                                                                                                                                                                                                                LEFT JOIN users AS u1 ON c.user_id = u1.user_id
                                                                                                                                                                                                                                LEFT JOIN users AS censor ON c.censor_id = censor.user_id
                                                                                                                                                                                                                                LEFT JOIN categories as cate ON c.category_id = cate.category_id
                                                                                                                                                                                                                                LEFT JOIN course_types as ct ON c.course_type_id = ct.course_type_id
WHERE c.status = 1 AND c.course_name LIKE CONCAT('%', searchName, '%');
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GetAllCourseProcedurePaging
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetAllCourseProcedurePaging`;
delimiter ;;
CREATE PROCEDURE `GetAllCourseProcedurePaging`(IN searchName VARCHAR(255),
                                               IN page INT,
                                               IN pageSize INT,
                                               IN course_type_name VARCHAR(255))
BEGIN
    DECLARE offset INT;
    SET offset = (page - 1) * pageSize;

SELECT
    c.course_id,
    c.course_file,
    c.course_name,
    c.course_video,
    c.free_numbers,
    c.image,
    c.price,
    c.sale_price,
    c.status,
    u1.fullname as username,
    censor.username as censor_name,
    cate.category_name,
    ct.type_name
FROM
    courses AS c
        LEFT JOIN
    users AS u1 ON c.user_id = u1.user_id
        LEFT JOIN
    users AS censor ON c.censor_id = censor.user_id
        LEFT JOIN
    categories as cate ON c.category_id = cate.category_id
        LEFT JOIN
    course_types as ct ON c.course_type_id = ct.course_type_id
WHERE
    c.status = 1
  AND c.course_name LIKE CONCAT('%', searchName, '%')
  AND (course_type_name = "" OR ct.type_name = course_type_name)
    LIMIT
offset, pageSize;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GetAllSalaryTeacher
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetAllSalaryTeacher`;
delimiter ;;
CREATE PROCEDURE `GetAllSalaryTeacher`()
BEGIN
SELECT
    u.user_id,
    u.username,
    u.fullname AS user,
    SUM(od.price)*(SELECT ratio FROM policies LIMIT 1)/100 AS salary
FROM
    users AS u
    LEFT JOIN user_roles AS ur ON u.user_id = ur.user_id
    LEFT JOIN roles AS r ON r.role_id = ur.role_id
    LEFT JOIN courses AS c ON u.user_id = c.user_id
    LEFT JOIN order_details AS od ON od.course_id = c.course_id
    LEFT JOIN orders AS o ON od.order_id= o.order_id
WHERE
    MONTH(o.update_at) = MONTH(CURDATE()) AND YEAR(o.update_at) = YEAR(CURDATE()) AND r.role_name = 'ROLE_TEACHER'
GROUP BY
    u.user_id,
    u.username,
    u.fullname;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GetAuthoritiesByUsername
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetAuthoritiesByUsername`;
delimiter ;;
CREATE PROCEDURE `GetAuthoritiesByUsername`(IN usernametk VARCHAR(255))
BEGIN
SELECT u.username, r.role_name AS role
FROM users u
         INNER JOIN user_roles ur ON u.user_id = ur.user_id
         INNER JOIN roles r ON ur.role_id = r.role_id
WHERE u.username = usernametk;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GetExamById
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetExamById`;
delimiter ;;
CREATE PROCEDURE `GetExamById`(IN testId INT)
BEGIN
SELECT
    exams.exam_id,
    exams.exam_name,
    exams.description,
    questions.question_id,
    questions.question,
    answers.answer_id,
    answers.answer_content
FROM exams
         LEFT JOIN questions ON exams.exam_id = questions.exam_id
         LEFT JOIN answers ON questions.question_id = answers.question_id
WHERE exams.exam_id = testId
ORDER BY questions.question_id, answers.answer_id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GetOrderDetailByUserIdProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetOrderDetailByUserIdProcedure`;
delimiter ;;
CREATE PROCEDURE `GetOrderDetailByUserIdProcedure`(IN user_id_search INT)
BEGIN
SELECT users.user_id, users.username, orders.description, courses.course_name, courses.course_id FROM users
                                                                                                          LEFT JOIN orders on users.user_id = orders.user_id
                                                                                                          LEFT JOIN order_details on orders.order_id = order_details.order_id
                                                                                                          LEFT JOIN courses on order_details.course_id = courses.course_id

WHERE users.user_id = user_id_search;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for getOrderDetailsByUserAndDateProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `getOrderDetailsByUserAndDateProcedure`;
delimiter ;;
CREATE PROCEDURE `getOrderDetailsByUserAndDateProcedure`(IN userId INT,
                                                         IN startDate DATE,
                                                         IN endDate DATE)
BEGIN
SELECT od.* , c.course_name, u2.username, u2.fullname
FROM order_details od
         INNER JOIN courses c ON od.course_id = c.course_id
         INNER JOIN users u ON c.user_id = u.user_id
         INNER JOIN orders o ON od.order_id = o.order_id
         INNER JOIN users u2 ON o.user_id = u2.user_id
WHERE u.user_id = userId
  AND o.create_at >= startDate
  AND o.create_at <= endDate;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GetUserByUsername
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetUserByUsername`;
delimiter ;;
CREATE PROCEDURE `GetUserByUsername`(IN usernametk VARCHAR(255))
BEGIN
SELECT username, password, status FROM users WHERE username = usernametk;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GetUserDoExamProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetUserDoExamProcedure`;
delimiter ;;
CREATE PROCEDURE `GetUserDoExamProcedure`(IN exam_id_search INT)
BEGIN
SELECT
    u.fullname,
    SUM(a.tof = 'true') AS TrueCount,
    COUNT(*) AS TotalCount,
    u.email,
    u.phone_number
FROM
    user_answers AS ua
        LEFT JOIN answers AS a ON ua.answer_id = a.answer_id
        LEFT JOIN users AS u ON ua.user_id = u.user_id
WHERE
    ua.exam_id = exam_id_search
GROUP BY
    ua.user_id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for RevenueChartDayTeacherProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `RevenueChartDayTeacherProcedure`;
delimiter ;;
CREATE PROCEDURE `RevenueChartDayTeacherProcedure`(IN userId INT)
BEGIN
SELECT
    HOUR(o.update_at) AS hour,
    SUM(od.price) AS total_revenue
FROM
    order_details AS od
    LEFT JOIN courses as c ON od.course_id = c.course_id
    LEFT JOIN orders as o ON od.order_id = o.order_id
WHERE
    o.update_at >= CURDATE() AND (userId = 0 OR c.user_id = userId) AND o.status = 1
GROUP BY
    HOUR(o.update_at);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for RevenueChartMonthTeacherProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `RevenueChartMonthTeacherProcedure`;
delimiter ;;
CREATE PROCEDURE `RevenueChartMonthTeacherProcedure`(IN userId INT)
BEGIN
SELECT
    DAY(o.update_at) AS day,
    SUM(od.price) AS total_revenue
FROM
    order_details AS od
    LEFT JOIN courses as c ON od.course_id = c.course_id
    LEFT JOIN orders as o ON od.order_id = o.order_id
WHERE
    MONTH(o.update_at) = MONTH(CURDATE()) AND YEAR(o.update_at) = YEAR(CURDATE()) AND (userId = 0 OR c.user_id = userId) AND o.status = 1
GROUP BY
    DAY(o.update_at)
ORDER BY
    DAY(o.update_at);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for RevenueChartWeekTeacherProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `RevenueChartWeekTeacherProcedure`;
delimiter ;;
CREATE PROCEDURE `RevenueChartWeekTeacherProcedure`(IN userId INT)
BEGIN
SELECT
    DAYOFWEEK(o.update_at) AS day,
      SUM(od.price) AS total_revenue
FROM
    order_details AS od
    LEFT JOIN courses as c ON od.course_id = c.course_id
    LEFT JOIN orders as o ON od.order_id = o.order_id
WHERE
    YEARWEEK(o.update_at, 1) = YEARWEEK(CURDATE(), 1) AND (userId = 0 OR c.user_id = userId) AND o.status = 1
GROUP BY
    DAYOFWEEK(o.update_at)
ORDER BY
    DAYOFWEEK(o.update_at);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for RevenueChartYearTeacherProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `RevenueChartYearTeacherProcedure`;
delimiter ;;
CREATE PROCEDURE `RevenueChartYearTeacherProcedure`(IN userId INT)
BEGIN
SELECT
    MONTH(o.update_at) AS month_num,
    SUM(od.price) AS total_revenue
FROM
    order_details AS od
    LEFT JOIN courses as c ON od.course_id = c.course_id
    LEFT JOIN orders as o ON od.order_id = o.order_id
WHERE
    YEAR(o.update_at) = YEAR(CURDATE()) AND (userId = 0 OR c.user_id = userId) AND o.status = 1
GROUP BY
    MONTH(o.update_at)
ORDER BY
    month_num;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for RevenueTeacherProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `RevenueTeacherProcedure`;
delimiter ;;
CREATE PROCEDURE `RevenueTeacherProcedure`(IN userId INT, IN startDate DATETIME, IN endDate DATETIME)
BEGIN
SELECT
    *
FROM
    order_details AS od
        LEFT JOIN courses as c ON od.course_id = c.course_id
        LEFT JOIN orders as o ON od.order_id = o.order_id
WHERE
    o.update_at >= startDate AND o.update_at <= endDate AND c.user_id = userId AND o.status = 1;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TotalDayProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `TotalDayProcedure`;
delimiter ;;
CREATE PROCEDURE `TotalDayProcedure`()
BEGIN
SELECT
    HOUR(o.update_at) AS hour,
    SUM(o.total) AS total_revenue
FROM
    orders as o
WHERE
    o.update_at >= CURDATE() AND o.status = 1
GROUP BY
    HOUR(o.update_at);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TotalMonthProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `TotalMonthProcedure`;
delimiter ;;
CREATE PROCEDURE `TotalMonthProcedure`()
BEGIN
SELECT
    DAY(o.update_at) AS day,
    SUM(o.total) AS total_revenue
FROM
    orders AS o
WHERE
    MONTH(o.update_at) = MONTH(CURDATE()) AND YEAR(o.update_at) = YEAR(CURDATE()) AND o.status = 1
GROUP BY
    DAY(o.update_at)
ORDER BY
    DAY(o.update_at);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TotalWeekProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `TotalWeekProcedure`;
delimiter ;;
CREATE PROCEDURE `TotalWeekProcedure`()
BEGIN
SELECT
    DAYOFWEEK(o.update_at) AS day,
      SUM(o.total) AS total_revenue
FROM
    orders as o
WHERE
    YEARWEEK(o.update_at, 1) = YEARWEEK(CURDATE(), 1) AND o.status = 1
GROUP BY
    DAYOFWEEK(o.update_at)
ORDER BY
    DAYOFWEEK(o.update_at);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TotalYearProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `TotalYearProcedure`;
delimiter ;;
CREATE PROCEDURE `TotalYearProcedure`()
BEGIN
SELECT
    MONTH(o.update_at) AS month_num,
    SUM(o.total) AS total_revenue
FROM
    orders AS o
WHERE
    YEAR(o.update_at) = YEAR(CURDATE()) AND o.status = 1
GROUP BY
    MONTH(o.update_at)
ORDER BY
    month_num;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;



INSERT INTO `roles` VALUES (1, 'USER', 'ROLE_USER', 1, '2024-05-18 16:25:00.000000');
INSERT INTO `roles` VALUES (2, 'ADMIN', 'ROLE_ADMIN', 1, '2024-05-21 21:49:21.943000');
INSERT INTO `roles` VALUES (3, 'TEACHER', 'ROLE_TEACHER', 1, '2024-05-21 21:46:00.000000');

INSERT INTO `users` VALUES (1, 'Ha Noi', '2024-05-09 00:00:00.000000', '2024-05-20 18:26:00.000000', 'Người dùng', 'ngominhkhoi262003@gmail.com', 'Ngo Khoi', 0, '/home/images/NoImage.png', '$2a$10$AGT4qVwntlv6xr5D9gA/jutbyvxoBvpsVCbZnd2dngmEuWy2GWX9a', '03241341', 1, '2024-05-20 18:26:00.000000', 'user1');
INSERT INTO `users` VALUES (2, 'New York', '1744-02-05 00:00:00.000000', '2024-05-20 18:28:00.000000', 'Quản trị', 'admin@gmail.com', 'Admin', 0, '/home/images/NoImage.png', '$2a$10$ZWwMJyAOcfoIZge.kdA55.4AlbDGvldG3XWqsaCnYucLOmR5A2KnS', '0123456789', 1, '2024-05-20 18:28:00.000000', 'admin1');

INSERT INTO `user_roles` VALUES (1, 1, 1);
INSERT INTO `user_roles` VALUES (2, 1, 2);
INSERT INTO `user_roles` VALUES (3, 2, 2);

INSERT INTO `categories` VALUES (2, 'Tài liệu', '2024-05-22 02:13:34.000000', 'Tài liệu và bài kiểm tra', 1);

INSERT INTO `category_sales` VALUES (1, 'Ratio', 'sale by ratio');
INSERT INTO `category_sales` VALUES (2, 'Number', 'sale by $ numbers off');

INSERT INTO `policies` VALUES (NULL, NULL, 60, 1, NOW(), 2);