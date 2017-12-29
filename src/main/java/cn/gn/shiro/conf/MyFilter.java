package cn.gn.shiro.conf;



import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gn.shiro.dao.UserMapper;
import cn.gn.shiro.entity.Menu;

@Component
public class MyFilter extends AuthorizationFilter {
		

		/*@Autowired
		private ShiroFilterFactoryBean sffb;*/

		@Autowired
		UserMapper userMapper;
		/**
		 * ƥ��ָ�������������url
		 * 
		 * @param regex
		 * @param url
		 * @return
		 */
		public static boolean matchUrl(String regex, String url) {
			//�����ݿ��url ���еĶ��һ����ֵ�'/' �滻��һ��'/' 
			regex = regex.replaceAll("/+", "/");
			//������ݿ��url�ʹ����urlƥ��  
			if (regex.equals(url)) {
				return true;
			}
			//������ݿ��url����'.' �滻��'\.'    '\.'��������ʽ�� �ȼ��� �� '.'
			regex = regex.replaceAll("\\.", "\\\\.");
			// /login.html /l*.html
			//������ݿ��url���� '*' �滻��'.*' '.*'��������ʽ�б�ʾ�����ַ�����0�λ���
			regex = regex.replaceAll("\\*", ".*");
			// /**/login.html /a/b/login.html
			//�������'*'�滻��'.*' ���ݿ�·������  '/**/' ��
			if (regex.indexOf("/.*.*/") >= 0) {
				//ת��"/\\.\\*\\.\\*/" = "/**/"    �滻��  "((/.*/)+|/)"  ƥ�� ��ͷ/ �м� ��������  ��β /   ������ʽ  "/.*/"
				//���ݿ���/**/*.js ��ת��Ϊ java������ʽ   /.*/.*.js
				//���ݿ���/Student/* ��ת��Ϊ java������ʽ  "/student/.*"
				regex = regex.replaceAll("/\\.\\*\\.\\*/", "((/.*/)+|/)");
			}
			System.out.println(regex + "----" + url);
			return Pattern.matches(regex, url);
		}
		
		/**
		 * ����
		 * 
		 * @param args
		 */
		public static void main(String[] args) {
			System.out.println(matchUrl("/**/s*.html", "/t/g/login.html"));
			System.out.println(Pattern.matches("/a\\.html", "/a.html"));
			System.out.println(Pattern.matches("/student/.*", "/student/{id}"));
			System.out.println(Pattern.matches("((/.*/)+|/)", "/s/b/a/a/a/a"));
			System.out.println(Pattern.matches("/.*/.*.js", "/ShiroLesson/thre/aaaaaaa/a.js"));
			
			String b = "\\*";
			System.out.println(b);
			String aString =  "a*b*c*d*e*f*G";
			String arr[] = aString.split(b);
			System.out.println(arr.length);
		}

		/**
		 * ��map��ģ�� ���Ҳ���Խ������������ݿ���
		 */
	/*	static {
			urls.put("/login.html", "anon");
			urls.put("/loginBlog", "anon");
			urls.put("/un.jsp", "anon");
			urls.put("/queryBlogByToken", "anon");
			urls.put("/query.jsp", "authc");
			urls.put("/add.jsp", "roles[role1]");
		}*/

		/**
		 * isAccessAllowed�����жϵ�ǰurl�������Ƿ�����֤ͨ�� �����֤ʧ��
		 * ���ø����onAccessDenied������ת����¼ʧ��ҳ������Ȩʧ��ҳ��
		 */
		@Override
		protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
				throws Exception {
			HttpServletRequest req = (HttpServletRequest) request;
			String contextPath = req.getContextPath();
			//��ȡ�û�������Դ��·��
			String url = req.getRequestURI();
			url=url.split(contextPath)[1];
			//��ȡ��Щurl��Ҫ��Щ��֤
			/*List<Menu> queryMenu = userMapper.queryMenuByUrl(url);*/
			List<Menu> queryMenu = userMapper.queryMenu();
			//���ݿ�û�����õ�ǰurl����Ȩ
			if(queryMenu.size()==0){
				return false;
			}
			String urlAuth = null;
			for(Menu menu:queryMenu){
				if(matchUrl(menu.getMenuUrl(), url)){
					urlAuth=menu.getMenuFilter();
				}
				
			}
			// ͨ��url��ȡ��Ȩ����
			/*String urlAuth = queryMenu.get(0).getMenuFilter();*/
			if (urlAuth == null) {
				return false;
			}
			// ���õĹ�������anon ֱ�ӷŹ�
			if (urlAuth.startsWith("anon")) {
				return true;
			}
			// ���õ���authc �жϵ�ǰ�û��Ƿ���֤ͨ��
			Subject subject = getSubject(request, response);
			if (urlAuth.startsWith("authc")) {
				return subject.isAuthenticated();
			}
			// ��Ȩ��֤ Ҳ��Ҫ�ж��Ƿ��¼ û�е�¼���� ��¼�����������֤
			boolean ifAuthc = subject.isAuthenticated();
			if (!ifAuthc)
				return ifAuthc;
			// ����Ƕ����roles������ ��ȡ���е�roles һ����roles[a,b]
			if (urlAuth.startsWith("roles")) {
				String[] rolesArray = urlAuth.split("roles\\[")[1].split("\\]")[0].split(",");
				if (rolesArray == null || rolesArray.length == 0) {
					return true;
				}
				Set<String> roles = CollectionUtils.asSet(rolesArray);
				return subject.hasAllRoles(roles);
			}
			if (urlAuth.startsWith("perms")) {
				String[] perms = urlAuth.split("perms\\[")[1].split("\\]")[0].split(",");
				boolean isPermitted = true;
				if (perms != null && perms.length > 0) {
					if (perms.length == 1) {
						if (!subject.isPermitted(perms[0])) {
							isPermitted = false;
						}
					} else {
						if (!subject.isPermittedAll(perms)) {
							isPermitted = false;
						}
					}
				}

				return isPermitted;
			}
			return false;
		}

	}

