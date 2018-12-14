package com.mondiamedia.tech.task.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mondiamedia.tech.task.entities.VideoEntity;

@Repository
public interface VideoRepository extends PagingAndSortingRepository<VideoEntity, Long> {
	
	VideoEntity findByVideoId(String videoId);
	
	@Query(value="select * from Videos v INNER JOIN users u ON v.user_id = u.user_id order by v.id desc", nativeQuery=true) 
	
	Page<VideoEntity> findAllOrderByIdDesc(Pageable pageableRequestString);
	
	Page<VideoEntity> findByUserId(Pageable pageableRequestString, String userId);
	
	Page<VideoEntity> findByCategory(Pageable pageableRequestString, String categoryName);
	
	@Query(value="select * from Videos v where v.video_id = :videoId and v.user_id = :userId", nativeQuery=true) 
	VideoEntity findByUserIdAndVideoId(@Param("userId") String userId, @Param("videoId") String videoId);
}
