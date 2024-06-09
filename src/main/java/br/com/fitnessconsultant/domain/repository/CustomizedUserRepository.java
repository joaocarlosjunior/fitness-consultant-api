package br.com.fitnessconsultant.domain.repository;

public interface CustomizedUserRepository<ID> {
    void deletedById(ID userId);
}
