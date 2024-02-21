package edu.tomerbu.blogfinalproject2024.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "arik")
public record ArikProps(String firstName, String lastName) {
}
