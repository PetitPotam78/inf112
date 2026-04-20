/**
 * Test package for the SocialNetwork application.
 *
 * Tests are organised by method (rule P14 - test step by step) :
 *   - InitTest        : checks that a fresh social network is correctly empty
 *   - AddMemberTest   : checks all the rules for adding members
 *   - AddItemBookTest : checks all the rules for adding books
 *   - SocialNetworkTest : launches the full test suite at once
 *   - TestReport      : utility class to agregate and display test results
 *
 * For each tested method, we verify :
 *   - nominal cases (everything works fine)
 *   - error cases (invalid parameters, duplicates, etc.)
 *   - that the network is not modified when an error occures
 */
package tests;
