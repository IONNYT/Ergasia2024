package gr.hua.dit.omada27.Services;

import gr.hua.dit.omada27.Entities.Request;
import gr.hua.dit.omada27.Entities.ApprovalStatus;
import gr.hua.dit.omada27.Repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    // dependency declaration
    private final RequestRepository requestRepository;

    // dependency injection βάσει constructor
    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Optional<Request> getRequestById(Long id) {
        return requestRepository.findById(id);
    }

    public List<Request> getRequestsByRenterId(Long renterId) {
        return requestRepository.findByRenterId(renterId);
    }

    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(Enum.valueOf(ApprovalStatus.class, status.toUpperCase()));
    }

    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    public List<Request> getRequestsByLandlordAndStatus(Long landlordId, ApprovalStatus approvalStatus) {
        return requestRepository.findByLandlordIdAndApprovalStatus(landlordId, approvalStatus);
    }


}
