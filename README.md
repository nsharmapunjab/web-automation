# NBA Stats Website Test Plan

## 1. Objective

This test plan outlines the automated testing strategy for the NBA Stats website (stats.nba.com) to ensure data consistency and performance across different pages and statistical views.

**Primary Goals:**
- Verify data consistency between team stats and standings pages
- Validate player statistics accuracy across leaders and individual player pages
- Ensure acceptable page loading performance for user experience

## 2. Scope

### 2.1 Pages Under Test
- **Team Stats Page**: http://stats.nba.com/teams/traditional/?sort=W_PCT&dir=-1
- **Standings Page**: https://stats.nba.com/standings/
- **Leaders Page**: http://stats.nba.com/leaders/
- **Individual Player Pages**: Dynamic URLs based on player selection

### 2.2 Features Under Test
- Team win statistics aggregation and consistency
- Player performance statistics (PPG, APG, RPG)
- Page loading performance and responsiveness
- Data accuracy across multiple page sources

### 2.3 Out of Scope
- User authentication and login functionality
- Mobile responsiveness testing
- Cross-browser compatibility (limited to Chrome)
- API testing (focus on UI validation only)

## 3. Environment & Tools

### 3.1 Technical Stack
- **Programming Language**: Java 11
- **Test Framework**: TestNG 7.8.0
- **Automation Tool**: Selenium WebDriver 4.15.0
- **Browser**: Chrome (latest stable version)
- **Reporting**: Allure Framework 2.24.0
- **Build Tool**: Maven 3.9.0
- **CI/CD**: Jenkins Pipeline

### 3.2 Test Environment
- **Base URL**: https://stats.nba.com
- **Browser Mode**: Non-headless (for debugging), Headless (for CI/CD)
- **Timeouts**: 30 seconds explicit wait, 10 seconds implicit wait
- **Performance Threshold**: 4 seconds for stats section loading

## 4. Test Cases

### 4.1 Team Wins Consistency Test

**Test Case ID**: TC001
**Priority**: Critical
**Objective**: Verify total wins for Eastern and Western conferences match between stats and standings pages

**Test Steps**:
1. Navigate to team stats page
2. Extract all team data and calculate total wins by conference
3. Navigate to standings page
4. Extract team data from Eastern and Western conference tables
5. Calculate total wins for each conference
6. Compare totals between both pages

**Expected Results**:
- Eastern Conference total wins match between both pages
- Western Conference total wins match between both pages
- Total wins are greater than 0

**Acceptance Criteria**:
- Zero tolerance for discrepancies in win totals
- Test must complete within 60 seconds

### 4.2 Top Players Statistics Verification Test

**Test Case ID**: TC002
**Priority**: Critical
**Objective**: Verify top 3 players' statistics match between leaders page and individual player pages

**Test Steps**:
1. Navigate to leaders page
2. Extract top 3 players from points, assists, and rebounds leaders
3. For each player:
   a. Navigate to their individual page
   b. Extract PPG, APG, and RPG statistics
   c. Compare with values from leaders page
4. Verify all statistics match within acceptable tolerance

**Expected Results**:
- All PPG values match within 0.1 tolerance
- All APG values match within 0.1 tolerance
- All RPG values match within 0.1 tolerance

**Acceptance Criteria**:
- Maximum tolerance of 0.1 for statistical differences
- All 3 players must be successfully verified
- Test must complete within 120 seconds

### 4.3 Performance Test

**Test Case ID**: TC003
**Priority**: Normal
**Objective**: Verify player pages load within acceptable time limits

**Test Steps**:
1. Navigate to leaders page
2. Get top 3 players' page URLs
3. For each player page:
   a. Measure navigation time
   b. Measure stats section loading time
   c. Calculate total loading time
4. Verify loading time is within threshold

**Expected Results**:
- Each player page loads completely within 4 seconds
- Stats section becomes interactive within threshold

**Acceptance Criteria**:
- Maximum loading time: 4000 milliseconds
- All measurements must be accurate to millisecond precision

## 5. Bug Reporting Process

### 5.1 Bug Classification
- **Critical**: Data inconsistencies, complete page failures
- **High**: Performance issues exceeding thresholds
- **Medium**: Minor UI issues, tooltip errors
- **Low**: Cosmetic issues, minor text discrepancies

### 5.2 Bug Report Template
