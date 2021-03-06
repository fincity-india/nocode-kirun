import { StringFormatter } from '../../../util/string/StringFormatter';
import { StringUtil } from '../../../util/string/StringUtil';
import { ExpressionEvaluationException } from '../exception/ExpressionEvaluationException';
import { TokenValueExtractor } from './TokenValueExtractor';

const KEYWORDS: Map<string, any> = new Map([
    ['true', true],
    ['false', false],
    ['null', undefined],
]);

export class LiteralTokenValueExtractor extends TokenValueExtractor {
    public static readonly INSTANCE: LiteralTokenValueExtractor = new LiteralTokenValueExtractor();

    protected getValueInternal(token: string): any {
        if (StringUtil.isNullOrBlank(token)) return undefined;

        token = token.trim();

        if (KEYWORDS.has(token)) return KEYWORDS.get(token);

        if (token.startsWith('"')) {
            return this.processString(token);
        }

        return this.processNumbers(token);
    }

    private processNumbers(token: string): any {
        try {
            let ind: number = token.indexOf('.');
            let v: number;
            if (ind == -1) {
                v = parseInt(token);
            } else {
                v = parseFloat(token);
            }

            if (isNaN(v)) throw new Error('Parse number error');

            return v;
        } catch (err) {
            throw new ExpressionEvaluationException(
                token,
                StringFormatter.format('Unable to parse the literal or expression $', token),
                err,
            );
        }
    }

    private processString(token: string): any {
        if (!token.endsWith('"'))
            throw new ExpressionEvaluationException(
                token,
                StringFormatter.format('String literal $ is not closed properly', token),
            );

        return token.substring(1, token.length - 1);
    }

    public getPrefix(): string {
        return '';
    }
}
