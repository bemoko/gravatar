package gravatar.tests;

import gravatar.Gravatar;

class GravatarTest extends GroovyTestCase  {
    
    
    void testGravatarUrlNoDefaults() {
        def gravatarProvider=new Gravatar()
        gravatarProvider.initialise [:]
        
        assertEquals("default url not correct",'http://www.gravatar.com/avatar/945681e509445cec02cde6488a6e6e0f?s=80',gravatarProvider.getImageUrl("support@bemoko.com"))
        assertTrue("wrong size",gravatarProvider.getImageUrl("support@bemoko.com",40).endsWith('?s=40'))
        assertTrue("wrong default url",gravatarProvider.getImageUrl("support@bemoko.com",'http://bemoko.com/logo.gif').endsWith('d=http%3A%2F%2Fbemoko.com%2Flogo.gif'))
        String tester=gravatarProvider.getImageUrl("support@bemoko.com",40,"http://bemoko.com/logo.gif")
        assertTrue("wrong size or url",tester.contains('s=40') && tester.contains('d=http%3A%2F%2Fbemoko.com%2Flogo.gif'))
    }
    
    void testGravatarUrlDefaults() {
        def gravatarProvider=new Gravatar()
        gravatarProvider.initialise (['defaultImageUrl':'http://bemoko.com/logo.gif','rating':'p','defaultImageSize':25])
        
        String tester=gravatarProvider.getImageUrl("support@bemoko.com")
        assertTrue("wrong size",tester.contains('?s=25') &&  !tester.contains('?s=80'))
        assertTrue("wrong default url",tester.contains('d=http%3A%2F%2Fbemoko.com%2Flogo.gif'))
        assertTrue("wrong rating",tester.contains('r=p'))
    }
    
    void testGravatarUrlDefaultOverrides() {
        def gravatarProvider=new Gravatar()
        gravatarProvider.initialise (['defaultImageUrl':'http://bemoko.com/logo.gif','rating':'p','defaultImageSize':25])
        
        String tester=gravatarProvider.getImageUrl("support@bemokoalternative.com",40,"http://bemokoalternative.com/logo.gif")
        assertTrue("wrong email md5",tester.contains('e69fdeec1733ff12b9ef1c9b2a512cc6') && !tester.contains('945681e509445cec02cde6488a6e6e0f'))
        assertTrue("wrong size",tester.contains('s=40') && !tester.contains('s=25'))
        assertTrue("wrong default url",tester.contains('d=http%3A%2F%2Fbemokoalternative.com%2Flogo.gif'))
    }
}
