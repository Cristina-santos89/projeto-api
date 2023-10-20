package projeto.dio.service.impl;

import projeto.dio.domain.model.User;
import projeto.dio.repository.UserRepository;
import projeto.dio.service.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User userToCreate) {
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getRegistration())) {
            throw new IllegalArgumentException("Este número de matrícula já existe");
        }
        return userRepository.save(userToCreate);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("O Usuário já deletado");
    }

    @Override
    public Object update(Long id, User model) {
        throw new UnsupportedOperationException("Usuário já incluso");
    }

    @Override
    public Object findAll() {
        throw new UnsupportedOperationException("Localizados dos os usuários no banco");
    }
    
}
