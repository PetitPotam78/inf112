/**
 * Custom exceptions for the SocialNetwork application.
 *
 * Each exception has one precise responsability (rule Po2) :
 *   - BadEntryException              : a parameter passed as argument is invalid (null, blank, out of range)
 *   - MemberAlreadyExistsException   : trying to add a member whose login is already taken
 *   - NotMemberException             : the login/password does not match any registred member
 *   - ItemBookAlreadyExistsException : trying to add a book whose title already exists
 *   - ItemFilmAlreadyExistsException : trying to add a film whose title already exists
 *   - NotItemException               : the requested item does not exist in the network
 *   - NotTestReportException         : the values passed to TestReport are inconsistant
 */
package exceptions;
