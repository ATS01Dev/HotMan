package bj.ats.hm.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(bj.ats.hm.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Client.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Codebadge.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Rooms.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Badge.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Badge.class.getName() + ".codes", jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Groupe.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Groupe.class.getName() + ".clients", jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Personel.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Personel.class.getName() + ".horraires", jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Fonction.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Department.class.getName(), jcacheConfiguration);
            cm.createCache(bj.ats.hm.domain.Horraires.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
