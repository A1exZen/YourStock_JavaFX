package server.TCP;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import server.TCP.enums.ResponseType;
import server.dto.SupplierDTO;
import server.dto.UserDTO;
import server.enums.RoleType;
import server.mapper.SupplierMapper;
import server.mapper.UserMapper;
import server.models.Employee;
import server.models.Supplier;
import server.models.User;
import server.services.SupplierService;
import server.services.UserService;
import server.utils.PasswordEncoder;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientThread implements Runnable {

    private Socket clientSocket;
    private Request request;
    private Gson gson;
    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket clientSocket) throws IOException {
        request = new Request();
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    // Инициализация сервисов для работы с данными
    private final UserService userService = UserService.getInstance();
    private final SupplierService supplierService = SupplierService.getInstance();

    //при запуске потока
    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                // Читаем запрос от клиента и преобразуем его из JSON в объект Request
                request = gson.fromJson(in.readLine(), Request.class);
                System.out.println(gson.toJson(request));
                // Обработка запроса на основе его типа
                switch (request.getRequestType()) {
                    case REGISTER: {
                        try {
                            UserDTO userDTO = gson.fromJson(request.getMessage(), UserDTO.class);

                            if (userService.usernameExists(userDTO.getUsername())) {
                                out.println(gson.toJson(new Response("Пользователь уже зарегистрирован!", ResponseType.BAD_REQUEST)));
                            } else {
                                User user = new User();
                                user.setUsername(userDTO.getUsername());
                                user.setPassword(userDTO.getPassword());
                                user.setRole(userDTO.getRole().name());
                                user.setEmployee(null);
                                userService.save(user);
                                out.println(gson.toJson(new Response("Вы успешно зарегистрированы!", ResponseType.OK)));
                            }
                        } catch (JsonSyntaxException e) {
                            out.println(gson.toJson(new Response("Ошибка в формате данных!", ResponseType.BAD_REQUEST)));
                        }
                        out.flush();
                        break;
                    }
                    case LOGIN: {
                        try {
                            UserDTO userDTO = gson.fromJson(request.getMessage(), UserDTO.class);
                            Optional<User> userDB = userService.findByUsername(userDTO.getUsername());


                            if (userDB.isPresent() && PasswordEncoder.checkPassword(userDTO.getPassword(), userDB.get().getPassword())) {
                                User foundUser = userDB.get();
                                UserDTO responseUserDTO = UserMapper.toDTO(foundUser);
                                out.println(gson.toJson(new Response(gson.toJson(responseUserDTO), ResponseType.OK)));
                                System.out.println(gson.toJson(new Response(gson.toJson(responseUserDTO), ResponseType.OK)));
                            } else {
                                out.println(gson.toJson(new Response("Неверный логин или пароль", ResponseType.NOT_FOUND)));
                            }
                        } catch (JsonSyntaxException e) {
                            out.println(gson.toJson(new Response("Ошибка в формате данных!", ResponseType.BAD_REQUEST)));
                        }
                        out.flush();
                        break;
                    }


                    case GET_ALL_USERS: {
                        out.println(gson.toJson(new Response(gson.toJson(userService.findAll()), ResponseType.OK)));
                        out.flush();
                        break;
                    }
//                    case ADD_ROLE_MANAGER: {
//                        Integer id = gson.fromJson(request.getMessage(), Integer.class);
//                        User user = userService.FindById(id);
//                        if (user == null) {
//                            out.println(gson.toJson(new Response(gson.toJson("Пользователь не найден"), ResponseType.NOT_FOUND)));
//                            break;
//                        }
//                        String message;
//                        if (user.getRoles().contains(Role.MANAGER)) {
//                            user.getRoles().remove(Role.MANAGER);
//                            message = "Роль удалена";
//                        } else {
//                            user.getRoles().add(Role.MANAGER);
//                            message = "Добавлена новая роль";
//                        }
//                        userService.Update(user);
//                        out.println(gson.toJson(new Response(gson.toJson(message), ResponseType.OK)));
//                        out.flush();
//                        break;
//                    }
                    case DELETE_USER: {
                        Integer id = gson.fromJson(request.getMessage(), Integer.class);
                        if (userService.findById(id) == null) {
                            out.println(gson.toJson(new Response(gson.toJson("Пользователь не найден"), ResponseType.NOT_FOUND)));
                        } else userService.deleteById(id);
                        out.println(gson.toJson(new Response(gson.toJson("Пользователь с id " + id + " был удалён"), ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case UPDATE_USER: {
                        User user = gson.fromJson(request.getMessage(), User.class);
                        Optional<User> userDB = userService.findByUsername(user.getUsername());
                        if (userDB.isPresent() && !userDB.get().getId().equals(user.getId())) {
                            out.println(gson.toJson(new Response("Данный логин уже используется", ResponseType.BAD_REQUEST)));
                        } else {
                            userService.update(user);
                            out.println(gson.toJson(new Response("Информация успешно обновлена", ResponseType.OK)));
                        }
                        out.flush();
                        break;
                    }
                    case CREATE_SUPPLIER: {
                        try {
                            SupplierDTO supplierDTO = gson.fromJson(request.getMessage(), SupplierDTO.class);
                            Supplier supplier = SupplierMapper.fromDTO(supplierDTO);

                            if (supplier.getName() == null || supplier.getName().isEmpty()) {
                                out.println(gson.toJson(new Response("Имя поставщика не может быть пустым!", ResponseType.BAD_REQUEST)));
                                out.flush();
                                break;
                            }
                            if (supplierService.supplierExists(supplier.getName())) {
                                System.out.println("Уже существует");
                                out.println(gson.toJson(new Response("Поставщик с таким именем уже существует!", ResponseType.BAD_REQUEST)));
                                out.flush();
                                break;
                            }
                            // Сохранение поставщика в базе данных
                            supplierService.save(supplier);

                            // Отправка успешного ответа клиенту
                            out.println(gson.toJson(new Response("Поставщик успешно добавлен!", ResponseType.OK)));
                        } catch (JsonSyntaxException e) {
                            out.println(gson.toJson(new Response("Ошибка в формате данных!", ResponseType.BAD_REQUEST)));
                        }
                        out.flush();
                        break;
                    }
                    case UPDATE_SUPPLIER: {
                        try {
                            SupplierDTO supplierDTO = gson.fromJson(request.getMessage(), SupplierDTO.class);

                            // Проверяем, что поставщик с указанным id существует
                            Supplier existingSupplier = supplierService.findById(supplierDTO.getId());
                            if (existingSupplier == null) {
                                out.println(gson.toJson(new Response("Поставщик не найден!", ResponseType.NOT_FOUND)));
                                out.flush();
                                break;
                            }

                            // Обновляем данные поставщика
                            Supplier supplierToUpdate = SupplierMapper.fromDTO(supplierDTO);
                            supplierService.update(supplierToUpdate);

                            out.println(gson.toJson(new Response("Поставщик успешно обновлен!", ResponseType.OK)));
                        } catch (JsonSyntaxException e) {
                            out.println(gson.toJson(new Response("Ошибка в формате данных!", ResponseType.BAD_REQUEST)));
                        }
                        out.flush();
                        break;
                    }
                    case DELETE_SUPPLIER: {
                        SupplierDTO supplierDTO = gson.fromJson(request.getMessage(), SupplierDTO.class);
                        Supplier supplier = SupplierMapper.fromDTO(supplierDTO);
                        System.out.println("Поставщик с ID " + supplier.getId() + " будет удален");
                        Supplier existingSupplier = supplierService.findById(supplier.getId());

                        Integer supplierId = existingSupplier.getId();
                        System.out.println("Поставщик с ID " + supplierId + " будет удален");
                        if (supplierService.findById(supplierId) == null) {
                            System.out.println("Поставщик не найден");
                            out.println(gson.toJson(new Response(gson.toJson("Поставщик не найден"), ResponseType.NOT_FOUND)));
                        } else {
                            supplierService.deleteById(supplierId);
                        } ;
                        System.out.println("Поставщик с ID " + supplierId + " успешно удалён");
                        out.println(gson.toJson(new Response("Поставщик с ID " + supplierId + " успешно удалён", ResponseType.OK)));
                        out.flush();
                        break;
                    }
                    case GET_ALL_SUPPLIERS: {
                        List<Supplier> suppliers = supplierService.findAll();
                        List<SupplierDTO> supplierDTOs = suppliers.stream()
                                .map(SupplierMapper::toDTO)
                                .collect(Collectors.toList());
                        String suppliersJson = new Gson().toJson(supplierDTOs);
                        out.println(new Gson().toJson(new Response(suppliersJson, ResponseType.OK)));
                        out.flush();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
