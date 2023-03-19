package hello.core.beanFind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextSameBeanType {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시에 같은 타입이 둘 이상일 경우 중복 오류가 발생.")
    void findBeanByTypeDuplicate(){
//        MemberRepository memberRepository = ac.getBean(MemberRepository.class);
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () ->ac.getBean(MemberRepository.class));
//        System.out.println("memberRepository = " + memberRepository);
//        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
//        assertThat(memberRepository).isInstanceOf(MemoryMemberRepository.class);
    }

    @Test
    @DisplayName("타입으로 조회 시에 같은 타입이 둘 이상일 경우 Bean 이름을 지정하여 검색.")
    void findBeanByNameDuplicate(){
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
//        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () ->ac.getBean(MemberRepository.class));
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
        assertThat(memberRepository).isInstanceOf(MemoryMemberRepository.class);
    }

    @Test
    @DisplayName("타입으로 조회 시에 같은 타입이 둘 이상일 경우 특정 타입을 모두 조회하기")
    void findAllBeanByType(){
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for(String key : beansOfType.keySet()){
            System.out.println("key = " + key + ", value = " + beansOfType.get(key));
        }
//        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () ->ac.getBean(MemberRepository.class));
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }


    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
