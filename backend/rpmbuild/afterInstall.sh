#!/bin/bash
systemctl daemon-reload
systemctl enable spring-auth-backend.service
systemctl restart spring-auth-backend.service
