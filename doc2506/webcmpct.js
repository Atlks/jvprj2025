
import { LitElement, html, css } from 'lit';

class RegisterForm extends LitElement {
    static properties = {
        username: { type: String },
        password: { type: String },
        error: { type: String }
    };

    constructor() {
        super();
        this.username = '';
        this.password = '';
        this.error = '';
    }

    static styles = css`
    input { margin: 5px; }
    p { color: red; }
  `;

    handleSubmit(e) {
        e.preventDefault();
        if (!this.username || !this.password) {
            this.error = '用户名和密码不能为空';
        } else {
            console.log('注册成功', this.username, this.password);
            this.error = '';
        }
    }

    render() {
        return html`
      <form @submit=${this.handleSubmit}>
        <input .value=${this.username} @input=${e => this.username = e.target.value} placeholder="用户名" />
        <input type="password" .value=${this.password} @input=${e => this.password = e.target.value} placeholder="密码" />
        <button type="submit">注册</button>
        ${this.error ? html`<p>${this.error}</p>` : ''}
      </form>
    `;
    }
}

customElements.define('register-form', RegisterForm);
