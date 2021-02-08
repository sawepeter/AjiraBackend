package com.devsawe.demo.Service;

import com.devsawe.demo.entities.User;
import com.devsawe.demo.entities.UserRatingModel;
import com.devsawe.demo.entities.WorkerProfile;
import com.devsawe.demo.repositories.UserRatingRepository;
import com.devsawe.demo.repositories.UserRepository;
import com.devsawe.demo.repositories.WorkersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkersRepository workersRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    public ReviewService() {
    }


   /* public boolean processReview(Long employerId, Long workerId, Double rating){

        //get the currently logged in employer id
        User currentEmployer = userRepository.findByUserId(employerId);

        if (currentEmployer == null){
            currentEmployer = userRepository.save(new User(employerId));
        }

        //get the id of the user to be rated
        WorkerProfile workerProfile = (WorkerProfile) workersRepository.findByUserId(workerId);

        if (workerProfile == null){
            workerProfile = workersRepository.save(new WorkerProfile(workerId));
        }

        UserRatingModel userRatingModel = userRatingRepository.save(new UserRatingModel(currentEmployer,workerProfile, rating, "None"));

        return true;
    }*/
}
